package io.github.javieravellan.reservabutacas.infra.web.request;

import java.util.List;

public record CreatingBookingRequest(
        String documentNumber,
        String customerName,
        String phoneNumber,
        String customerEmail,
        short customerAge,
        long billboardMovieId,
        List<Long> seatIds
) {
}
