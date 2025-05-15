package io.github.javieravellan.reservabutacas.domain;

import java.time.LocalDateTime;
import java.util.List;

public record BillboardRecord(
        long id,
        LocalDateTime date,
        LocalDateTime startTime,
        LocalDateTime endTime,
        List<BillboardMovieRecord> billboardMovies
) {}
