package io.github.javieravellan.reservabutacas.infra.mapper;

import io.github.javieravellan.reservabutacas.domain.SeatRecord;
import io.github.javieravellan.reservabutacas.infra.entity.Room;
import io.github.javieravellan.reservabutacas.infra.entity.Seat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeatMapper {
    public static SeatRecord toDto(Seat seat) {
        return new SeatRecord(
                seat.getId(),
                seat.getNumber(),
                seat.getRowNumber(),
                seat.isStatus(),
                seat.getRoomId(),
                seat.getRoom().getName()
        );
    }

    public static Seat toEntity(SeatRecord seatRecord) {
        var seat = new Seat();
        seat.setId(seatRecord.id());
        seat.setNumber(seatRecord.number());
        seat.setRowNumber(seatRecord.rowNumber());
        seat.setStatus(seatRecord.status());

        // add roomId
        var room = new Room();
        room.setId(seatRecord.roomId());
        seat.setRoom(room);

        return seat;
    }
}
