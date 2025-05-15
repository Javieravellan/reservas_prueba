package io.github.javieravellan.reservabutacas.infra.web.response;

import org.springframework.http.HttpStatus;

public record BodyErrorVM(
    String message,
    String error,
    String path,
    int statusCode,
    long timestamp
) {}
