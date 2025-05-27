package io.github.javieravellan.reservabutacas.infra.repository;

import io.github.javieravellan.reservabutacas.infra.entity.Billboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillboardRepository extends JpaRepository<Billboard, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find a billboard by its name:
    @Query(nativeQuery = true, value = "SELECT * FROM billboards WHERE start_time::date = CURRENT_DATE")
    Optional<Billboard> findOneBillboardAvailableToday();
}
