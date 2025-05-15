package io.github.javieravellan.reservabutacas.domain;

import io.github.javieravellan.reservabutacas.infra.entity.MovieGenre;

public record MovieShort(String name, MovieGenre genre) {
}
