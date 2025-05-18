package io.github.javieravellan.reservabutacas.domain;

import java.time.LocalDateTime;
import java.util.List;

public record BookingRecord(
        long id,
        LocalDateTime date,
        LocalDateTime showTime,
        CustomerRecord customer,
        long billboardId,
        String billboardName,
        List<SeatRecord> seats,
        MovieShort movie
) {
}
