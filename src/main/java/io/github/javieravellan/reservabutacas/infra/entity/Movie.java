package io.github.javieravellan.reservabutacas.infra.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "movies")
public class Movie extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MovieGenre genre;
    @Column(name = "allowed_age", nullable = false)
    private short allowedAge;
    @Column(name = "length_minutes", nullable = false)
    private int lengthMinutes;
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billboard_id", nullable = false)
    private Billboard billboard;*/
}
