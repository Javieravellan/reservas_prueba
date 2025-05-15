package io.github.javieravellan.reservabutacas.infra.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "seats")
public class Seat extends BaseEntity {
    private short number;
    private short rowNumber;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public long getRoomId() {
        return room.getId();
    }
}
