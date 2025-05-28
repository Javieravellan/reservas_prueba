package io.github.javieravellan.reservabutacas.domain;

import java.time.LocalDateTime;

public record BillboardMovieRecord(
        Long id,
        MovieShort movie,
        RoomRecord room,
        Long billboardId,
        LocalDateTime showTime
) {
}
