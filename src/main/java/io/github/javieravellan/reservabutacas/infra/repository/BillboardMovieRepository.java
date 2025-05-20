package io.github.javieravellan.reservabutacas.infra.repository;

import io.github.javieravellan.reservabutacas.infra.entity.BillboardMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillboardMovieRepository extends JpaRepository<BillboardMovie, Long> {
    void deleteAllByMovieId(long movieId);
}
