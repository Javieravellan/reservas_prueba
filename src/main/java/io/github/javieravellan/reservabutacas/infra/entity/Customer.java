package io.github.javieravellan.reservabutacas.infra.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "customers", indexes = {
    @Index(name = "idx_customer_document_number", columnList = "document_number", unique = true)
})
public class Customer extends BaseEntity {
    @Column(name = "document_number", length = 20, nullable = false)
    private String documentNumber;
    @Column(name = "name", length = 30)
    private String name;
    @Column(name = "last_name", length = 30)
    private String lastName;
    @Column(name = "age", nullable = false)
    private short age;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
}
