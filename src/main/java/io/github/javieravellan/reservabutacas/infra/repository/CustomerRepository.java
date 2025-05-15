package io.github.javieravellan.reservabutacas.infra.repository;

import io.github.javieravellan.reservabutacas.infra.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Custom query methods can be defined here if needed
    // For example, findByEmail(String email) or findByName(String name)
    // JpaRepository provides basic CRUD operations out of the box
}
