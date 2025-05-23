package io.github.javieravellan.reservabutacas.infra.service;

import io.github.javieravellan.reservabutacas.application.SeatSecondaryPort;
import io.github.javieravellan.reservabutacas.domain.SeatRecord;
import io.github.javieravellan.reservabutacas.infra.exception.CustomRequestException;
import io.github.javieravellan.reservabutacas.infra.mapper.SeatMapper;
import io.github.javieravellan.reservabutacas.infra.repository.RoomRepository;
import io.github.javieravellan.reservabutacas.infra.repository.SeatRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Log4j2
public class SeatSecondaryAdapter implements SeatSecondaryPort {
    private final SeatRepository seatRepository;
    private final RoomRepository roomRepository;
    public SeatSecondaryAdapter(SeatRepository seatRepository, RoomRepository roomRepository) {
        this.seatRepository = seatRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional // se setea esta anotación para poder interactuar con las relaciones lazy
    public SeatRecord createSeat(SeatRecord seatRecord) {
        // Convert SeatRecord to Seat entity
        var seat = SeatMapper.toEntity(seatRecord);
        seat.setId(null);
        // obtener la sala
        roomRepository.findById(seatRecord.roomId())
                .ifPresentOrElse(seat::setRoom, () -> {
                    throw new CustomRequestException("Sala no encontrada", HttpStatus.NOT_FOUND);
                });
        // Guardar la butaca
        log.info("Guardando butaca: {}", seat);
        return SeatMapper.toDto(seatRepository.save(seat));
    }

    @Override
    @Transactional
    public SeatRecord getSeatById(long seatId) {
        return seatRepository.findById(seatId)
                .map(SeatMapper::toDto)
                .orElseThrow(() -> new CustomRequestException("Butaca no encontrada", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public SeatRecord updateSeat(long seatId, SeatRecord seatRecord) {
        seatRepository.findById(seatId)
                .orElseThrow(() -> new CustomRequestException("Butaca no encontrada", HttpStatus.NOT_FOUND));
        // Convert SeatRecord to Seat entity
        var seat = SeatMapper.toEntity(seatRecord);
        seat.setId(seatId);
        return SeatMapper.toDto(seatRepository.save(seat));
    }

    @Override
    @Transactional
    public List<SeatRecord> getAllSeats() {
        return seatRepository.findAll()
                .stream()
                .map(SeatMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<SeatRecord> getAllSeatsByRoomId(long roomId) {
        return seatRepository.findByRoomIdIn(List.of(roomId))
                .stream()
                .map(SeatMapper::toDto)
                .toList();
    }
}
