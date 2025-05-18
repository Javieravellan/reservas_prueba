package io.github.javieravellan.reservabutacas.infra.service;

import io.github.javieravellan.reservabutacas.application.BookingSecondaryPort;
import io.github.javieravellan.reservabutacas.domain.BookingRecord;
import io.github.javieravellan.reservabutacas.domain.CustomerRecord;
import io.github.javieravellan.reservabutacas.domain.MovieShort;
import io.github.javieravellan.reservabutacas.domain.SeatRecord;
import io.github.javieravellan.reservabutacas.infra.entity.*;
import io.github.javieravellan.reservabutacas.infra.exception.CustomRequestException;
import io.github.javieravellan.reservabutacas.infra.repository.BookingRepository;
import io.github.javieravellan.reservabutacas.infra.repository.CustomerRepository;
import io.github.javieravellan.reservabutacas.infra.repository.SeatRepository;
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

    public BookingSecondaryAdapter(BookingRepository bookingRepository, SeatRepository seatRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.customerRepository = customerRepository;
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
                    seatsPeerBooking.stream().map(seat -> new SeatRecord(seat.getId(), seat.getNumber(), seat.getRowNumber(), seat.isStatus(), booking.getRoomId()))
                            .toList(),
                    new MovieShort(0, booking.getPelicula(), booking.getGenero())
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
    public void createBooking(BookingRecord bookingRecord) {
        Booking booking = new Booking();
        booking.setDate(LocalDateTime.now());
        // buscar cliente por cédula
        customerRepository.findOneByDocumentNumber(booking.getCustomerDocumentNumber())
                .ifPresentOrElse(booking::setCustomer, () -> {
                    // guardar cliente y asignar a la reserva
                    var customer = new Customer();
                    customer.setName(bookingRecord.customer().name());
                    customer.setDocumentNumber(bookingRecord.customer().documentNumber());
                    customer.setEmail(bookingRecord.customer().email());
                    customer.setLastName(bookingRecord.customer().lastName());
                    customer.setPhoneNumber(bookingRecord.customer().phoneNumber());
                    customer.setAge(bookingRecord.customer().age());
                    var customerSaved = customerRepository.save(customer);
                    booking.setCustomer(customerSaved);
                });
        booking.setSeats(bookingRecord.seats().stream()
                .map(seatRecord -> {
                    var seat = new Seat();
                    seat.setId(seatRecord.id());
                    seat.setNumber(seatRecord.number());
                    seat.setRowNumber(seatRecord.rowNumber());
                    seat.setStatus(false);
                    return seat;
                }).toList());

        // setear función
        var billboardMovie = new BillboardMovie();
        billboardMovie.setId(bookingRecord.billboardId());
        booking.setBillboardMovie(billboardMovie);

        bookingRepository.save(booking);
    }
}
