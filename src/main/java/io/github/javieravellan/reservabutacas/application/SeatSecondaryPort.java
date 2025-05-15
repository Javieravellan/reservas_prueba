package io.github.javieravellan.reservabutacas.application;

import io.github.javieravellan.reservabutacas.domain.SeatRecord;

import java.util.List;

public interface SeatSecondaryPort {
    SeatRecord createSeat(SeatRecord seatRecord);
    SeatRecord getSeatById(long seatId);
    SeatRecord updateSeat(long seatId, SeatRecord seatRecord);
    List<SeatRecord> getAllSeats();
    List<SeatRecord> getAllSeatsByRoomId(long roomId);
}
