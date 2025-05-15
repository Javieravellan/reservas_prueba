package io.github.javieravellan.reservabutacas.domain;

public record CustomerRecord(
        long id,
        String documentNumber,
        String name,
        String lastName,
        short age,
        String email,
        String phoneNumber
) {}
