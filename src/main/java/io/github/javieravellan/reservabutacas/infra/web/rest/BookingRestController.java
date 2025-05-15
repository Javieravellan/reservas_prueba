package io.github.javieravellan.reservabutacas.infra.web.rest;

import io.github.javieravellan.reservabutacas.application.BookingPrimaryPort;
import io.github.javieravellan.reservabutacas.domain.BookingRecord;
import io.github.javieravellan.reservabutacas.infra.web.response.BookingCancelResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingRestController {
    private final BookingPrimaryPort bookingPrimaryPort;

    public BookingRestController(BookingPrimaryPort bookingPrimaryPort) {
        this.bookingPrimaryPort = bookingPrimaryPort;
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingCancelResponse> cancelBookingAndActivateSeats(@PathVariable long bookingId) {
        var result = bookingPrimaryPort.cancelBookingAndActivateSeats(bookingId);
        if (result) {
            return ResponseEntity.ok(new BookingCancelResponse(true, "Reserva cancelada con éxito. Todas las butacas han sido liberadas."));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/billboard_movies/{billboardMovieId}")
    public ResponseEntity<Map<String, String>> cancelBillboardMovieAndActivateSeats(@PathVariable long billboardMovieId) {
        bookingPrimaryPort.cancelAllBookingsByBillboardMovieId(billboardMovieId);
        return ResponseEntity.ok(Map.of(
                "message", "Todas las reservas de la función han sido canceladas y los asientos han sido activados"
        ));
    }

    @GetMapping("/genre/horror")
    public ResponseEntity<List<BookingRecord>> getAllBookingsHorrorByPeriod(@RequestParam String startDate, @RequestParam String endDate) {
        var result = bookingPrimaryPort.getAllBookingsHorrorByPeriod(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
