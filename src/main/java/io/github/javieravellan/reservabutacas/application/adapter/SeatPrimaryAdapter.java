package io.github.javieravellan.reservabutacas.application.adapter;

import io.github.javieravellan.reservabutacas.application.SeatPrimaryPort;
import io.github.javieravellan.reservabutacas.application.SeatSecondaryPort;
import io.github.javieravellan.reservabutacas.application.Singleton;
import io.github.javieravellan.reservabutacas.domain.SeatRecord;

import java.util.List;

@Singleton
public class SeatPrimaryAdapter implements SeatPrimaryPort {
    private final SeatSecondaryPort seatSecondaryPort;

    public SeatPrimaryAdapter(SeatSecondaryPort seatSecondaryPort) {
        this.seatSecondaryPort = seatSecondaryPort;
    }

    @Override
    public SeatRecord createSeat(SeatRecord seatRecord) {
        return seatSecondaryPort.createSeat(seatRecord);
    }

    @Override
    public SeatRecord getSeatById(long seatId) {
        return seatSecondaryPort.getSeatById(seatId);
    }

    @Override
    public SeatRecord updateSeat(long seatId, SeatRecord seatRecord) {
        return seatSecondaryPort.updateSeat(seatId, seatRecord);
    }

    @Override
    public List<SeatRecord> getAllSeats() {
        return seatSecondaryPort.getAllSeats();
    }

    @Override
    public List<SeatRecord> getAllSeatsByRoomId(long roomId) {
        return seatSecondaryPort.getAllSeatsByRoomId(roomId);
    }
}
