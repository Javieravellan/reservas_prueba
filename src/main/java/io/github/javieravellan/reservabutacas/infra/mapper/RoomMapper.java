package io.github.javieravellan.reservabutacas.infra.mapper;

import io.github.javieravellan.reservabutacas.domain.RoomRecord;
import io.github.javieravellan.reservabutacas.domain.SeatRecord;
import io.github.javieravellan.reservabutacas.infra.entity.Room;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomMapper {
    public static Room toEntity(RoomRecord roomRecord) {
        var room = new Room();
        room.setId(roomRecord.id());
        room.setName(roomRecord.name());
        room.setNumber(roomRecord.number());
        room.setSeats(roomRecord.seats().stream()
                .map(SeatMapper::toEntity)
                .toList());
        return room;
    }

    public static RoomRecord toDto(Room room) {
        return new RoomRecord(
                room.getId(),
                room.getName(),
                room.getNumber(),
                room.getSeats().stream().map(seat -> new SeatRecord(
                        seat.getId(),
                        seat.getNumber(),
                        seat.getRowNumber(),
                        seat.isStatus(),
                        room.getId(),
                        room.getName()
                )).toList()
        );
    }
}
