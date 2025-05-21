package io.github.javieravellan.reservabutacas.application;

import io.github.javieravellan.reservabutacas.domain.RoomRecord;

import java.util.List;

public interface RoomSecondaryPort {
    RoomRecord createRoom(RoomRecord roomRecord);
    RoomRecord getRoomById(long roomId);
    RoomRecord updateRoom(long roomId, RoomRecord roomRecord);
    List<RoomRecord> getAllRooms();
    List<RoomRecord> getAllRoomsByCinemaId(long cinemaId);
}
