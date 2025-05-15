package io.github.javieravellan.reservabutacas.infra.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "bookings")
public class Booking extends BaseEntity {
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Seat> seats;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billboard_movie_id")
    private BillboardMovie billboardMovie;

    public long getRoomId() {
        return billboardMovie.getRoomId();
    }

    public String getCustomerName() {
        return customer.getName() + " " + customer.getLastName();
    }

    public String getCustomerDocumentNumber() {
        return customer.getDocumentNumber();
    }
}
