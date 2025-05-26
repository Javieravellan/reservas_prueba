package io.github.javieravellan.reservabutacas.infra.service;

import io.github.javieravellan.reservabutacas.application.BillboardSecondaryPort;
import io.github.javieravellan.reservabutacas.domain.BillboardRecord;
import io.github.javieravellan.reservabutacas.domain.SeatRecord;
import io.github.javieravellan.reservabutacas.infra.exception.CustomRequestException;
import io.github.javieravellan.reservabutacas.infra.mapper.BillboardMapper;
import io.github.javieravellan.reservabutacas.infra.repository.BillboardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BillboardSecondaryAdapter implements BillboardSecondaryPort {
    private final BillboardRepository billboardRepository;

    public BillboardSecondaryAdapter(BillboardRepository billboardRepository) {
        this.billboardRepository = billboardRepository;
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
        // Crear billboard
        var billboard = BillboardMapper.toEntity(billboardRecord);
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
    public BillboardRecord updateBillboard(long billboardId, BillboardRecord billboardRecord) {
        billboardRepository.findById(billboardId)
                .orElseThrow(() -> new CustomRequestException("Billboard not found", HttpStatus.NOT_FOUND));

        // Actualizar cartelera
        var billboard = BillboardMapper.toEntity(billboardRecord);
        billboard.setId(billboardId);
        return BillboardMapper.toDto(billboardRepository.save(billboard));
    }

    @Override
    @Transactional
    public void deleteBillboardById(long billboardId) {
        // Eliminar cartelera
        billboardRepository.deleteById(billboardId);
    }
}
