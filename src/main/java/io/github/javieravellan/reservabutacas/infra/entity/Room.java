package io.github.javieravellan.reservabutacas.infra.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "rooms")
public class Room extends BaseEntity {
    @Column(length = 50, nullable = false)
    private String name;
    @Column(nullable = false)
    private short number;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>(); // butacas de la sala
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillboardMovie> billboardMovies = new ArrayList<>(); // una sala puede proyectar varias películas
}
