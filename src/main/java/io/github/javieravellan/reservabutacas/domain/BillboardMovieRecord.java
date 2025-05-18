package io.github.javieravellan.reservabutacas.domain;

import java.time.LocalDateTime;

public record BillboardMovieRecord(
        long id,
        MovieShort movie,
        RoomRecord room,
        long billboardId,
        LocalDateTime showTime
) {
}
