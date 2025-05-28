package io.github.javieravellan.reservabutacas.infra.service;

import io.github.javieravellan.reservabutacas.application.MovieSecondaryPort;
import io.github.javieravellan.reservabutacas.domain.MovieShort;
import io.github.javieravellan.reservabutacas.infra.repository.MovieRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class MovieSecondaryAdapter implements MovieSecondaryPort {
    private final MovieRepository movieRepository;

    public MovieSecondaryAdapter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
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
        log.info("Obteniendo todas las películas.");
        return movieRepository.findAll().stream()
                .map(movie -> new MovieShort(
                        movie.getId(),
                        movie.getName(),
                        movie.getGenre()
                )).toList();
    }
}
