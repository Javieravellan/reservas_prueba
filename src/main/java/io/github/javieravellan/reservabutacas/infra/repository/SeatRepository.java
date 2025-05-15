package io.github.javieravellan.reservabutacas.infra.repository;

import io.github.javieravellan.reservabutacas.infra.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query(nativeQuery = true, value = """
            SELECT s.*
            FROM seats s
            JOIN rooms r ON s.room_id = r.id
            WHERE r.id IN (:roomIds)
            """)
    List<Seat> findByRoomIdIn(List<Long> roomIds);

    @Query(nativeQuery = true, value = """
        SELECT s.*
        FROM seats s
        JOIN bookings_seats bs ON s.id = bs.seats_id
        WHERE bs.booking_id = ?1
        """)
    List<Seat> findBookedSeatsByBookingId(Long bookingId);
}
