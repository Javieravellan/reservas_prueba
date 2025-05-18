package io.github.javieravellan.reservabutacas.application.adapter;

import io.github.javieravellan.reservabutacas.application.BillboardPrimaryPort;
import io.github.javieravellan.reservabutacas.application.BillboardSecondaryPort;
import io.github.javieravellan.reservabutacas.application.Singleton;
import io.github.javieravellan.reservabutacas.domain.BillboardRecord;
import io.github.javieravellan.reservabutacas.domain.SeatRecord;
import io.github.javieravellan.reservabutacas.domain.SeatsByRoomAtBillboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class BillboardPrimaryAdapter implements BillboardPrimaryPort {

    private final BillboardSecondaryPort billboardSecondaryPort;

    public BillboardPrimaryAdapter(BillboardSecondaryPort billboardSecondaryPort) {
        this.billboardSecondaryPort = billboardSecondaryPort;
    }

    @Override
    public List<SeatsByRoomAtBillboard> getAllSeatsPeerRoomByBillboardId(final long billboardId) {
        var seats = billboardSecondaryPort.getAllSeatsPeerRoomByBillboardId(billboardId);

        return new ArrayList<>(seats.stream().collect(Collectors.toMap(
                        SeatRecord::roomId,
                        seat -> {
                            if (!seat.status()) {
                                return new SeatsByRoomAtBillboard(
                                        billboardId,
                                        new HashSet<>(),
                                        new HashSet<>(Collections.singleton(seat))
                                );
                            }
                            return new SeatsByRoomAtBillboard(
                                    billboardId,
                                    new HashSet<>(Collections.singleton(seat)),
                                    new HashSet<>()
                            );
                        },
                        (existing, replacement) -> {
                            existing.seatsAvailable().addAll(replacement.seatsAvailable());
                            existing.seatsReserved().addAll(replacement.seatsReserved());
                            return existing;
                        }
                ))
                .values());
    }

    @Override
    public BillboardRecord createBillboard(BillboardRecord billboardRecord) {
        return billboardSecondaryPort.createBillboard(billboardRecord);
    }

    @Override
    public List<BillboardRecord> getAllBillboards() {
        return billboardSecondaryPort.getAllBillboards();
    }

    @Override
    public BillboardRecord getBillboardById(long billboardId) {
        return billboardSecondaryPort.getBillboardById(billboardId);
    }

    @Override
    public BillboardRecord getBillboardAvailableToday() {
        return billboardSecondaryPort.getBillboardAvailableToday();
    }

    @Override
    public BillboardRecord updateBillboard(long billboardId, BillboardRecord billboardRecord) {
        return billboardSecondaryPort.updateBillboard(billboardId, billboardRecord);
    }

    @Override
    public void deleteBillboardById(long billboardId) {
        billboardSecondaryPort.deleteBillboardById(billboardId);
    }
}
