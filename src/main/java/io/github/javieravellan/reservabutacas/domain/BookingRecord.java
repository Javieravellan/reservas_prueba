package io.github.javieravellan.reservabutacas.domain;

import java.time.LocalDateTime;
import java.util.List;

public record BookingRecord(
        long id,
        LocalDateTime date,
        LocalDateTime showTime,
        long customerId,
        long billboardId,
        String customerName,
        String billboardName,
        List<SeatRecord> seats,
        MovieShort movie
) {
}
