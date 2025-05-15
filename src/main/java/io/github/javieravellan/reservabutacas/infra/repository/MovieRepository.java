package io.github.javieravellan.reservabutacas.infra.repository;

import io.github.javieravellan.reservabutacas.infra.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Custom query methods can be defined here if needed
    // For example, findByTitle(String title) or findByGenre(String genre)
    // JpaRepository provides basic CRUD operations out of the box
}
