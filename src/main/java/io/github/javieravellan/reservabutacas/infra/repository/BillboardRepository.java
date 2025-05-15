package io.github.javieravellan.reservabutacas.infra.repository;

import io.github.javieravellan.reservabutacas.infra.entity.Billboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillboardRepository extends JpaRepository<Billboard, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find a billboard by its name:
    // Optional<Billboard> findByName(String name);
}
