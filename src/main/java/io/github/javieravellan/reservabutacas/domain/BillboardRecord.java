package io.github.javieravellan.reservabutacas.domain;

import java.time.LocalDateTime;
import java.util.List;

public record BillboardRecord(
        Long id,
        LocalDateTime date,
        boolean status,
        LocalDateTime startTime,
        LocalDateTime endTime,
        List<BillboardMovieRecord> billboardMovies
) {}
