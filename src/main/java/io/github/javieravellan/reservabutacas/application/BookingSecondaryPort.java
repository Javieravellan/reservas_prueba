package io.github.javieravellan.reservabutacas.application;

import io.github.javieravellan.reservabutacas.domain.BookingRecord;
import io.github.javieravellan.reservabutacas.infra.web.request.CreatingBookingRequest;

import java.time.LocalDate;
import java.util.List;

public interface BookingSecondaryPort {
    List<BookingRecord> getAllBookingsHorrorByPeriod(LocalDate startDate, LocalDate endDate);
    boolean cancelBookingAndActivateSeats(long bookingId);
    void cancelAllBookingsByBillboardMovieId(long billboardMovieId);
    void createBooking(CreatingBookingRequest bookingRecord);
}
