package io.github.javieravellan.reservabutacas.infra.repository;

import io.github.javieravellan.reservabutacas.infra.entity.Booking;
import io.github.javieravellan.reservabutacas.infra.entity.BookingHorrorResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>  {
    @Query(value = "SELECT * FROM get_bookings_horror_by_period(:startDate, :endDate)", nativeQuery = true)
    List<BookingHorrorResult> findBookingsHorrorByPeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    List<Booking> findByBillboardMovieId(long billboardMovieId);
}
