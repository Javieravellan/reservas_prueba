package io.github.javieravellan.reservabutacas.infra.web.rest;

import io.github.javieravellan.reservabutacas.application.SeatPrimaryPort;
import io.github.javieravellan.reservabutacas.domain.SeatRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/seats")
public class SeatRestController {
    private final SeatPrimaryPort seatPrimaryPort;

    public SeatRestController(SeatPrimaryPort seatPrimaryPort) {
        this.seatPrimaryPort = seatPrimaryPort;
    }

    @PostMapping
    public ResponseEntity<SeatRecord> createSeat(@RequestBody SeatRecord seatRecord) {
        return ResponseEntity.ok(seatPrimaryPort.createSeat(seatRecord));
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<SeatRecord> getSeatById(@PathVariable long seatId) {
        return ResponseEntity.ok(seatPrimaryPort.getSeatById(seatId));
    }

    @PutMapping("/{seatId}")
    public ResponseEntity<SeatRecord> updateSeat(@PathVariable long seatId, @RequestBody SeatRecord seatRecord) {
        return ResponseEntity.ok(seatPrimaryPort.updateSeat(seatId, seatRecord));
    }

    @GetMapping
    public ResponseEntity<List<SeatRecord>> getAllSeats() {
        return ResponseEntity.ok(seatPrimaryPort.getAllSeats());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<SeatRecord>> getAllSeatsByRoomId(@PathVariable long roomId) {
        return ResponseEntity.ok(seatPrimaryPort.getAllSeatsByRoomId(roomId));
    }

    @PatchMapping("/{seatId}/toggle-status")
    public ResponseEntity<Map<String, Object>> toggleSeatStatus(@PathVariable long seatId) {
        seatPrimaryPort.toggleSeatStatus(seatId);
        return ResponseEntity.ok(Map.of(
            "message", "Estado de la butaca actualizado correctamente",
            "seatId", seatId
        ));
    }

}
