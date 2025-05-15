package io.github.javieravellan.reservabutacas.domain;

import java.time.LocalDateTime;

public record BillboardMovieRecord(
        long id,
        long movieId,
        long roomId,
        long billboardId,
        LocalDateTime showTime
) {
}
