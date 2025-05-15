package io.github.javieravellan.reservabutacas.infra.entity;

import jakarta.persistence.*;
import lombok.Data;

//@Entity
@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
}
