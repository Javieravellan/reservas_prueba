package io.github.javieravellan.reservabutacas.domain;

import io.github.javieravellan.reservabutacas.infra.entity.MovieGenre;

public record MovieShort(long id, String name, MovieGenre genre) {
}
