package io.github.javieravellan.reservabutacas.domain;

import java.util.List;

public record RoomRecord(
        Long id,
        String name,
        short number,
        List<SeatRecord> seats
) {
}
