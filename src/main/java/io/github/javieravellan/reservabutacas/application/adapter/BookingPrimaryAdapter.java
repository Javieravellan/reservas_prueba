package io.github.javieravellan.reservabutacas.application.adapter;

import io.github.javieravellan.reservabutacas.application.BookingPrimaryPort;
import io.github.javieravellan.reservabutacas.application.BookingSecondaryPort;
import io.github.javieravellan.reservabutacas.application.Singleton;
import io.github.javieravellan.reservabutacas.domain.BookingRecord;

import java.time.LocalDate;
import java.util.List;

@Singleton
public class BookingPrimaryAdapter implements BookingPrimaryPort {

    private final BookingSecondaryPort bookingSecondaryPort;

    public BookingPrimaryAdapter(BookingSecondaryPort bookingSecondaryPort) {
        this.bookingSecondaryPort = bookingSecondaryPort;
    }

    @Override
    public List<BookingRecord> getAllBookingsHorrorByPeriod(LocalDate startDate, LocalDate endDate) {
        return bookingSecondaryPort.getAllBookingsHorrorByPeriod(startDate, endDate);
    }

    @Override
    public boolean cancelBookingAndActivateSeats(long bookingId) {
        return bookingSecondaryPort.cancelBookingAndActivateSeats(bookingId);
    }

    @Override
    public void cancelAllBookingsByBillboardMovieId(long billboardMovieId) {
        bookingSecondaryPort.cancelAllBookingsByBillboardMovieId(billboardMovieId);
    }

    @Override
    public void createBooking(BookingRecord bookingRecord) {

    }
}
