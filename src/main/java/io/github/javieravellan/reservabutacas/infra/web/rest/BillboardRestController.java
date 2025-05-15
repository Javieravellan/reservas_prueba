package io.github.javieravellan.reservabutacas.infra.web.rest;

import io.github.javieravellan.reservabutacas.application.BillboardPrimaryPort;
import io.github.javieravellan.reservabutacas.domain.BillboardRecord;
import io.github.javieravellan.reservabutacas.domain.SeatsByRoomAtBillboard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/billboards")
public class BillboardRestController {
    private final BillboardPrimaryPort billboardPrimaryPort;

    public BillboardRestController(BillboardPrimaryPort billboardPrimaryPort) {
        this.billboardPrimaryPort = billboardPrimaryPort;
    }

    @PostMapping
    public ResponseEntity<BillboardRecord> createBillboard(@RequestBody BillboardRecord billboardRecord) {
        var createdBillboard = billboardPrimaryPort.createBillboard(billboardRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBillboard);
    }

    @GetMapping
    public ResponseEntity<List<BillboardRecord>> getAllBillboards() {
        var billboards = billboardPrimaryPort.getAllBillboards();
        return ResponseEntity.ok(billboards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillboardRecord> getBillboardById(@PathVariable long id) {
        var billboard = billboardPrimaryPort.getBillboardById(id);
        return billboard != null ? ResponseEntity.ok(billboard) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<List<SeatsByRoomAtBillboard>> getAllSeatsPeerRoomByBillboardId(@PathVariable long id) {
        var seats = billboardPrimaryPort.getAllSeatsPeerRoomByBillboardId(id);
        return ResponseEntity.ok(seats);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillboardRecord> updateBillboard(@PathVariable long id, @RequestBody BillboardRecord billboardRecord) {
        var updatedBillboard = billboardPrimaryPort.updateBillboard(id, billboardRecord);
        return ResponseEntity.ok(updatedBillboard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBillboardById(@PathVariable long id) {
        billboardPrimaryPort.deleteBillboardById(id);
        return ResponseEntity.noContent().build();
    }

}
