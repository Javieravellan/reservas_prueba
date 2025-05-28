package io.github.javieravellan.reservabutacas.infra.service;

import io.github.javieravellan.reservabutacas.application.BookingSecondaryPort;
import io.github.javieravellan.reservabutacas.domain.BookingRecord;
import io.github.javieravellan.reservabutacas.domain.CustomerRecord;
import io.github.javieravellan.reservabutacas.domain.MovieShort;
import io.github.javieravellan.reservabutacas.domain.SeatRecord;
import io.github.javieravellan.reservabutacas.infra.entity.*;
import io.github.javieravellan.reservabutacas.infra.exception.CustomRequestException;
import io.github.javieravellan.reservabutacas.infra.repository.BillboardMovieRepository;
import io.github.javieravellan.reservabutacas.infra.repository.BookingRepository;
import io.github.javieravellan.reservabutacas.infra.repository.CustomerRepository;
import io.github.javieravellan.reservabutacas.infra.repository.SeatRepository;
import io.github.javieravellan.reservabutacas.infra.web.request.CreatingBookingRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class BookingSecondaryAdapter implements BookingSecondaryPort {
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final CustomerRepository customerRepository;
    private final BillboardMovieRepository billboardMovieRepository;

    public BookingSecondaryAdapter(BookingRepository bookingRepository, SeatRepository seatRepository, CustomerRepository customerRepository, BillboardMovieRepository billboardMovieRepository) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.customerRepository = customerRepository;
        this.billboardMovieRepository = billboardMovieRepository;
    }

    @Override
    public List<BookingRecord> getAllBookingsHorrorByPeriod(LocalDate startDate, LocalDate endDate) {
        List<BookingRecord> bookingRecords = new ArrayList<>();
        List<BookingHorrorResult> bookingHorrorResult = bookingRepository.findBookingsHorrorByPeriod(
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
            );

        for (var booking : bookingHorrorResult) {
            List<Seat> seatsPeerBooking = seatRepository.findBookedSeatsByBookingId(booking.getId()).stream()
                    .filter(seat -> seat.getRoom().getId() == booking.getRoomId())
                    .toList();

            // buscar el cliente
            var customer = customerRepository.findById(booking.getCustomerId()).orElseThrow();
            var bookingRecord = new BookingRecord(
                    booking.getId(),
                    booking.getDate(),
                    booking.getShowTime(),
                    new CustomerRecord(
                            booking.getId(),
                            customer.getDocumentNumber(),
                            customer.getName(),
                            customer.getLastName(),
                            customer.getAge(),
                            customer.getEmail(),
                            customer.getPhoneNumber()
                    ),
                    0,
                    booking.getRoomName(),
                    seatsPeerBooking.stream().map(seat -> new SeatRecord(
                            seat.getId(),
                            seat.getNumber(),
                            seat.getRowNumber(),
                            seat.isStatus(),
                            booking.getRoomId(),
                            booking.getRoomName()))
                        .toList(),
                    new MovieShort(null, booking.getPelicula(), booking.getGenero())
            );
            bookingRecords.add(bookingRecord);
        }
        return bookingRecords;
    }

    @Override
    @Transactional
    public boolean cancelBookingAndActivateSeats(long bookingId) {
        var bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            var booking = bookingOptional.get();
            var seats = seatRepository.findByRoomIdIn(List.of(booking.getRoomId()));
            for (var seat : seats) {
                seat.setStatus(true);
            }
            seatRepository.saveAll(seats);
            // eliminar la reserva
            bookingRepository.delete(booking);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void cancelAllBookingsByBillboardMovieId(long billboardMovieId) {
        bookingRepository.findByBillboardMovieId(billboardMovieId).forEach(booking -> {
            if (booking.getBillboardMovie().getShowTime().isBefore(LocalDateTime.now())) {
                log.warn("No se puede cancelar la reserva {} porque la función ya ha comenzado", booking.getId());
                throw new CustomRequestException("No se puede cancelar funciones de la cartelera con fecha anterior a la actual",
                        HttpStatus.CONFLICT);
            }
            var seats = seatRepository.findByRoomIdIn(List.of(booking.getRoomId()));
            for (var seat : seats) {
                seat.setStatus(true); // habilitar las butacas
            }
            seatRepository.saveAll(seats);
            bookingRepository.delete(booking);

            // imprimir lista de clientes afectados por la cancelación.
            log.info("Reserva cancelada: {} - Cliente: {} - CI: {}", booking.getId(),
                    booking.getCustomerName(), booking.getCustomerDocumentNumber());
        });
    }

    @Override
    @Transactional
    public void createBooking(CreatingBookingRequest bookingRecord) {
        log.info("Creando reserva para cliente: {}", bookingRecord.documentNumber());
        Booking booking = new Booking();
        booking.setDate(LocalDateTime.now());
        // buscar cliente por cédula
        customerRepository.findOneByDocumentNumber(bookingRecord.documentNumber())
            .ifPresentOrElse(booking::setCustomer, () -> {
                // guardar cliente y asignar a la reserva
                var customer = new Customer();
                customer.setName(bookingRecord.customerName());
                customer.setDocumentNumber(bookingRecord.documentNumber());
                customer.setEmail(bookingRecord.customerEmail());
                customer.setPhoneNumber(bookingRecord.phoneNumber());
                customer.setAge(bookingRecord.customerAge());
                var customerSaved = customerRepository.save(customer);
                booking.setCustomer(customerSaved);
            });

        if (bookingRecord.billboardMovieId() == 0) {
            log.error("No se encontró la función en cartelera");
            throw new CustomRequestException("No se encontró la función en cartelera", HttpStatus.NOT_FOUND);
        }
        billboardMovieRepository.findById(bookingRecord.billboardMovieId())
            .ifPresent(bill -> {
                if (bill.getAvailableSeats() < bookingRecord.seatIds().size()) {
                    log.error("No hay suficientes asientos disponibles para la reserva");
                    throw new CustomRequestException("No hay suficientes asientos disponibles para la reserva",
                            HttpStatus.CONFLICT);
                }

                var bookedSeats = bill.getRoom().getSeats().stream()
                        .filter(s -> bookingRecord.seatIds().stream().anyMatch(id -> s.getId().equals(id)))
                        .peek(seat -> seat.setStatus(false)).toList();
                booking.setSeats(bookedSeats);
                booking.setBillboardMovie(bill);
            });
        booking.setStatus(true);
        bookingRepository.save(booking);
        log.info("Reserva creada exitosamente. Cliente: {}", bookingRecord.documentNumber());
    }

    @Override
    @Transactional
    public List<BookingRecord> getAllBookingsByBillboardToday() {
        log.debug("Obteniendo todas las reservas del día");
        return bookingRepository.findAllByBillboardToday().stream()
            .map(booking -> new BookingRecord(
                        booking.getId(),
                        booking.getDate(),
                        booking.getBillboardMovie().getShowTime(),
                        new CustomerRecord(
                                booking.getCustomer().getId(),
                                booking.getCustomer().getDocumentNumber(),
                                booking.getCustomer().getName(),
                                booking.getCustomer().getLastName(),
                                booking.getCustomer().getAge(),
                                booking.getCustomer().getEmail(),
                                booking.getCustomer().getPhoneNumber()
                        ),
                        booking.getBillboard().getId(),
                        booking.getBillboardMovie().getRoomName(),
                        booking.getSeats().stream()
                                .map(seat -> new SeatRecord(
                                        seat.getId(),
                                        seat.getNumber(),
                                        seat.getRowNumber(),
                                        seat.isStatus(),
                                        booking.getRoomId(),
                                        seat.getRoom().getName()))
                                .toList(),
                        new MovieShort(booking.getMovie().getId(), booking.getMovie().getName(), booking.getMovie().getGenre())
                )
            ).toList();
    }

    @Override
    @Transactional
    public void deleteBooking(long bookingId) {
        log.info("Eliminando reserva con id: {}", bookingId);
        bookingRepository.findById(bookingId).ifPresentOrElse(booking -> {
            var seats = seatRepository.findBookedSeatsByBookingId(booking.getId());
            for (var seat : seats) {
                seat.setStatus(true);
            }
            seatRepository.saveAll(seats);
            bookingRepository.delete(booking);
            log.info("Reserva '{}' eliminada con éxito", bookingId);
        }, () -> {
            log.error("No se encontró la reserva con id: {}", bookingId);
            throw new CustomRequestException("No se encontró la reserva con id: " + bookingId, HttpStatus.NOT_FOUND);
        });
    }
}
