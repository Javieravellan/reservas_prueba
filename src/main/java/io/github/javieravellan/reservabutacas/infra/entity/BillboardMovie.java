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
@Table(name = "billboard_movies")
public class BillboardMovie extends BaseEntity {
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Room room;
    @ManyToOne
    private Billboard billboard;
    @Column(name = "show_time", nullable = false)
    private LocalDateTime showTime;
    @OneToMany(mappedBy = "billboardMovie")
    private List<Booking> bookings = new ArrayList<>();

    public int getAvailableSeats() {
        var capacity = room.getNumber();
        var bookedSeats = bookings.stream()
                .mapToInt(booking -> booking.getSeats().size())
                .sum();

        return capacity - bookedSeats;
    }

    public List<Seat> getSeats() {
        return room.getSeats();
    }

    public String getRoomName() {
        return room.getName();
    }

    public long getRoomId() {
        return room.getId();
    }
}
