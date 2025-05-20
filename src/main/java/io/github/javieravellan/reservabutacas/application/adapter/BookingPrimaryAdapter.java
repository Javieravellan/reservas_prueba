package io.github.javieravellan.reservabutacas.application.adapter;

import io.github.javieravellan.reservabutacas.application.BookingPrimaryPort;
import io.github.javieravellan.reservabutacas.application.BookingSecondaryPort;
import io.github.javieravellan.reservabutacas.application.Singleton;
import io.github.javieravellan.reservabutacas.domain.BookingRecord;
import io.github.javieravellan.reservabutacas.infra.web.request.CreatingBookingRequest;

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
    public void createBooking(CreatingBookingRequest bookingRecord) {
        bookingSecondaryPort.createBooking(bookingRecord);
    }

    @Override
    public List<BookingRecord> getAllBookingsByBillboardToday() {
        return bookingSecondaryPort.getAllBookingsByBillboardToday();
    }

    @Override
    public void deleteBooking(long bookingId) {
        bookingSecondaryPort.deleteBooking(bookingId);
    }
}
