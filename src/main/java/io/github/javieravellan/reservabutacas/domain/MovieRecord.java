package io.github.javieravellan.reservabutacas.domain;

import io.github.javieravellan.reservabutacas.infra.entity.MovieGenre;

public record MovieRecord(
        long id,
        String name,
        MovieGenre genre,
        short allowedAge,
        int lengthMinutes
) {
}
