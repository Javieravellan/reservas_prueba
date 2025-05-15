package io.github.javieravellan.reservabutacas.infra.entity;

import java.time.LocalDateTime;

public interface BookingHorrorResult {
    Long getId();
    LocalDateTime getDate();
    LocalDateTime getShowTime();
    String getPelicula();
    MovieGenre getGenero();
    long getCustomerId();
    long getRoomId();
    String getRoomName();
    String getCustomerName();
}
