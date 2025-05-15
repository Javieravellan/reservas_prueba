package io.github.javieravellan.reservabutacas.domain;

public record SeatRecord(
        long id,
        short number,
        short rowNumber,
        boolean status,
        long roomId
) {
}
