package io.github.javieravellan.reservabutacas.application;

import io.github.javieravellan.reservabutacas.domain.MovieShort;

import java.util.List;

public interface MoviePrimaryPort {
    // Define methods that the primary adapter will implement
    // For example:
    void createMovie(String title, String director, String genre);

    void updateMovie(Long id, String title, String director, String genre);

    void deleteMovie(Long id);

    MovieShort getMovieById(Long id);

    List<MovieShort> getAllMovies();
}
