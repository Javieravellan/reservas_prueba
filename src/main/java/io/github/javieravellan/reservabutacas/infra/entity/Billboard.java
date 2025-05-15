package io.github.javieravellan.reservabutacas.infra.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "billboards")
public class Billboard extends BaseEntity { // Representa una cartelera de cine
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;
    @OneToMany(mappedBy = "billboard", fetch = FetchType.LAZY)
    private List<BillboardMovie> billboardMovies = new ArrayList<>(); // estas serían las funciones de las películas

    public List<Movie> getMovies() {
        return billboardMovies.stream()
                .map(BillboardMovie::getMovie)
                .distinct()
                .toList();
    }
}
