package io.github.javieravellan.reservabutacas.infra.service;

import io.github.javieravellan.reservabutacas.application.RoomSecondaryPort;
import io.github.javieravellan.reservabutacas.domain.RoomRecord;
import io.github.javieravellan.reservabutacas.infra.mapper.RoomMapper;
import io.github.javieravellan.reservabutacas.infra.repository.RoomRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RoomSecondaryAdapter implements RoomSecondaryPort {
    private final RoomRepository roomRepository;

    public RoomSecondaryAdapter(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomRecord createRoom(RoomRecord roomRecord) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public RoomRecord getRoomById(long roomId) {
        return roomRepository.findById(roomId)
                .map(RoomMapper::toDto)
                .orElse(null);
    }

    @Override
    public RoomRecord updateRoom(long roomId, RoomRecord roomRecord) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public List<RoomRecord> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::toDto)
                .toList();
    }

    @Override
    public List<RoomRecord> getAllRoomsByCinemaId(long cinemaId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
