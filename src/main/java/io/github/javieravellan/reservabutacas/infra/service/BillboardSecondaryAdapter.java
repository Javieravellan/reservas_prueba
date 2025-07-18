package io.github.javieravellan.reservabutacas.infra.service;

import io.github.javieravellan.reservabutacas.application.BillboardSecondaryPort;
import io.github.javieravellan.reservabutacas.domain.BillboardRecord;
import io.github.javieravellan.reservabutacas.domain.SeatRecord;
import io.github.javieravellan.reservabutacas.infra.exception.CustomRequestException;
import io.github.javieravellan.reservabutacas.infra.mapper.BillboardMapper;
import io.github.javieravellan.reservabutacas.infra.repository.BillboardMovieRepository;
import io.github.javieravellan.reservabutacas.infra.repository.BillboardRepository;
import io.github.javieravellan.reservabutacas.infra.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillboardSecondaryAdapter implements BillboardSecondaryPort {
    private final BillboardRepository billboardRepository;
    private final BillboardMovieRepository billboardMovieRepository;
    private final BookingRepository bookingRepository;

    public BillboardSecondaryAdapter(BillboardRepository billboardRepository, BillboardMovieRepository billboardMovieRepository, BookingRepository bookingRepository) {
        this.billboardRepository = billboardRepository;
        this.billboardMovieRepository = billboardMovieRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public List<SeatRecord> getAllSeatsPeerRoomByBillboardId(long billboardId) {
        // Obtener el billboard por su ID
        var list = new ArrayList<SeatRecord>();
        billboardRepository.findById(billboardId).ifPresent(billboard ->
            // Obtener las funciones de la cartelera
            billboard.getBillboardMovies().forEach(billboardMovie -> {
                // Obtener los asientos de cada sala de las funciones de la cartelera
                List<SeatRecord> seats = billboardMovie.getSeats().stream()
                        .map(seat -> new SeatRecord(
                                seat.getId(),
                                seat.getNumber(),
                                seat.getRowNumber(),
                                seat.isStatus(),
                                billboardMovie.getRoomId(),
                                seat.getRoom().getName()
                        )).toList();
                list.addAll(seats);
            }));

        return list;
    }

    @Override
    @Transactional
    public BillboardRecord createBillboard(BillboardRecord billboardRecord) {
        if (billboardRecord.billboardMovies() == null || billboardRecord.billboardMovies().isEmpty()) {
            throw new CustomRequestException("La cartelera debe tener al menos una función registrada.", HttpStatus.BAD_REQUEST);
        }
        // Crear billboard
        var billboard = BillboardMapper.toEntity(billboardRecord);
        billboard.setDate(LocalDateTime.now());
        return BillboardMapper.toDto(billboardRepository.save(billboard));
    }

    @Override
    @Transactional
    public List<BillboardRecord> getAllBillboards() {
        // Obtener todas las carteleras
        var billboards = billboardRepository.findAll();
        // Mapear a BillboardRecord
        return billboards.stream()
                .map(BillboardMapper::toDto)
                .toList();
    }

    @Override
    public BillboardRecord getBillboardById(long billboardId) {
        // Obtener la cartelera por su ID
        var billboardOptional = billboardRepository.findById(billboardId);
        return billboardOptional.map(BillboardMapper::toDto).orElse(null);
    }

    @Override
    @Transactional
    public BillboardRecord getBillboardAvailableToday() {
        // Obtener la cartelera disponible hoy
        var billboardOptional = billboardRepository.findOneBillboardAvailableToday();
        return billboardOptional.map(b -> {
            b.setBillboardMovies(b.getBillboardMovies().stream()
                    .filter(bm -> bm.getShowTime().isAfter(LocalDateTime.now()))
                    .toList());
            return BillboardMapper.toDto(b);
        }).orElse(null);
    }

    @Override
    @Transactional
    public BillboardRecord updateBillboard(long billboardId, BillboardRecord billboardRecord) {
        if (billboardRecord.billboardMovies() == null || billboardRecord.billboardMovies().isEmpty()) {
            throw new CustomRequestException("La cartelera debe tener al menos una función registrada.", HttpStatus.BAD_REQUEST);
        }

        var billboardFound = billboardRepository.findById(billboardId)
                .orElseThrow(() -> new CustomRequestException("Billboard not found", HttpStatus.NOT_FOUND));

        var billboardMoviesToDelete = billboardFound.getBillboardMovies().stream()
                .filter(bm -> billboardRecord.billboardMovies().stream()
                        .noneMatch(bmr ->
                                bmr.movie().id().equals(bm.getMovie().getId()) &&
                                        bmr.room().id().equals(bm.getRoom().getId()) &&
                                        bmr.showTime().equals(bm.getShowTime())
                        ))
                .collect(Collectors.toList());

        if (!billboardMoviesToDelete.isEmpty()) {
            billboardMovieRepository.deleteAll(billboardMoviesToDelete);
            billboardFound.getBillboardMovies().removeAll(billboardMoviesToDelete);
        }
        // validar duplicados entre lo registrado y lo que se va a registrar
        var existingBillboardMovies = billboardFound.getBillboardMovies().stream()
                .map(bm -> bm.getMovie().getId() + "-" + bm.getRoomId() + "-" + bm.getShowTime())
                .collect(Collectors.toSet());

        for (var bmr : billboardRecord.billboardMovies()) {
            String key = bmr.movie().id() + "-" + bmr.room().id() + "-" + bmr.showTime();
            if (existingBillboardMovies.contains(key)) {
                billboardFound.getBillboardMovies()
                        .removeIf(bm -> {
                            billboardMovieRepository.delete(bm);
                            return (bm.getMovie().getId() + "-" + bm.getRoomId() + "-" + bm.getShowTime()).equals(key);
                        });
            }
        }


        BillboardMapper.partialUpdate(billboardFound, billboardRecord);
        billboardFound.setId(billboardId);
        return BillboardMapper.toDto(billboardRepository.save(billboardFound));
    }

    @Override
    @Transactional
    public void deleteBillboardById(long billboardId) {
        // Obtener billboard
        var billboardOptional = billboardRepository.findById(billboardId);
        if (billboardOptional.isEmpty()) {
            throw new CustomRequestException("Billboard not found", HttpStatus.NOT_FOUND);
        }

        var billboard = billboardOptional.get();

        if (!billboard.getBillboardMovies().isEmpty()) {
            billboard.getBillboardMovies().forEach(billboardMovie -> bookingRepository
                    .deleteAll(bookingRepository.findByBillboardMovieId(billboardMovie.getId())));
        }

        if (!billboard.getBillboardMovies().isEmpty()) {
            billboardMovieRepository.deleteAll(billboard.getBillboardMovies());
        }

        billboardRepository.delete(billboard);
    }
}
