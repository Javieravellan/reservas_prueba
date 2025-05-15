package io.github.javieravellan.reservabutacas.domain;

import java.util.Set;

public record SeatsByRoomAtBillboard(
        long billboardId,
        Set<SeatRecord> seatsAvailable,
        Set<SeatRecord> seatsReserved
) {}
