package io.github.javieravellan.reservabutacas.infra.web.rest;

import io.github.javieravellan.reservabutacas.application.MoviePrimaryPort;
import io.github.javieravellan.reservabutacas.domain.MovieShort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieRestController {
    private final MoviePrimaryPort moviePrimaryPort;

    public MovieRestController(MoviePrimaryPort moviePrimaryPort) {
        this.moviePrimaryPort = moviePrimaryPort;
    }

    @GetMapping
    public List<MovieShort> getAllMovies() {
        return moviePrimaryPort.getAllMovies();
    }
}
