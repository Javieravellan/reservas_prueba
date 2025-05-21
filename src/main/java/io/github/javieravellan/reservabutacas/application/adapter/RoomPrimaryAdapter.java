package io.github.javieravellan.reservabutacas.application.adapter;

import io.github.javieravellan.reservabutacas.application.RoomPrimaryPort;
import io.github.javieravellan.reservabutacas.application.RoomSecondaryPort;
import io.github.javieravellan.reservabutacas.application.Singleton;
import io.github.javieravellan.reservabutacas.domain.RoomRecord;

import java.util.List;

@Singleton
public class RoomPrimaryAdapter implements RoomPrimaryPort {
    private final RoomSecondaryPort roomSecondaryPort;

    public RoomPrimaryAdapter(RoomSecondaryPort roomSecondaryPort) {
        this.roomSecondaryPort = roomSecondaryPort;
    }

    @Override
    public RoomRecord createRoom(RoomRecord roomRecord) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public RoomRecord getRoomById(long roomId) {
        return roomSecondaryPort.getRoomById(roomId);
    }

    @Override
    public RoomRecord updateRoom(long roomId, RoomRecord roomRecord) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<RoomRecord> getAllRooms() {
        return roomSecondaryPort.getAllRooms();
    }

    @Override
    public List<RoomRecord> getAllRoomsByCinemaId(long cinemaId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
