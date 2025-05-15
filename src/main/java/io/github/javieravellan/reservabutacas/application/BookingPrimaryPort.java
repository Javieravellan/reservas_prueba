package io.github.javieravellan.reservabutacas.application;

import io.github.javieravellan.reservabutacas.domain.BookingRecord;

import java.time.LocalDate;
import java.util.List;

/// los primary port son las entradas a la aplicación
public interface BookingPrimaryPort {
    List<BookingRecord> getAllBookingsHorrorByPeriod(LocalDate startDate, LocalDate endDate);
    boolean cancelBookingAndActivateSeats(long bookingId);
    void cancelAllBookingsByBillboardMovieId(long billboardMovieId);
}
