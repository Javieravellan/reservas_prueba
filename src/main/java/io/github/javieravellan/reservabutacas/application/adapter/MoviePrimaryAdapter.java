package io.github.javieravellan.reservabutacas.application.adapter;

import io.github.javieravellan.reservabutacas.application.MoviePrimaryPort;
import io.github.javieravellan.reservabutacas.application.MovieSecondaryPort;
import io.github.javieravellan.reservabutacas.application.Singleton;
import io.github.javieravellan.reservabutacas.domain.MovieShort;

import java.util.List;

@Singleton
public class MoviePrimaryAdapter implements MoviePrimaryPort {
    private final MovieSecondaryPort movieSecondaryPort;

    public MoviePrimaryAdapter(MovieSecondaryPort movieSecondaryPort) {
        this.movieSecondaryPort = movieSecondaryPort;
    }

    @Override
    public void createMovie(String title, String director, String genre) {

    }

    @Override
    public void updateMovie(Long id, String title, String director, String genre) {

    }

    @Override
    public void deleteMovie(Long id) {

    }

    @Override
    public MovieShort getMovieById(Long id) {
        return null;
    }

    @Override
    public List<MovieShort> getAllMovies() {
        return movieSecondaryPort.getAllMovies();
    }
}
