package io.github.javieravellan.reservabutacas.infra.web.rest;

import io.github.javieravellan.reservabutacas.application.adapter.RoomPrimaryAdapter;
import io.github.javieravellan.reservabutacas.domain.RoomRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomRestController {
    private final RoomPrimaryAdapter roomPrimaryAdapter;

    public RoomRestController(RoomPrimaryAdapter roomPrimaryAdapter) {
        this.roomPrimaryAdapter = roomPrimaryAdapter;
    }

    @GetMapping
    public ResponseEntity<List<RoomRecord>> getAllRooms() {
        var rooms = roomPrimaryAdapter.getAllRooms();
        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomRecord> getRoomById(@PathVariable long roomId) {
        var room = roomPrimaryAdapter.getRoomById(roomId);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }
}
