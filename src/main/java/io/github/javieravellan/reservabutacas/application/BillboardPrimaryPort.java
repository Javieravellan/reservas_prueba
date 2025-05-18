package io.github.javieravellan.reservabutacas.application;

import io.github.javieravellan.reservabutacas.domain.BillboardRecord;
import io.github.javieravellan.reservabutacas.domain.SeatsByRoomAtBillboard;

import java.util.List;

public interface BillboardPrimaryPort {
    List<SeatsByRoomAtBillboard> getAllSeatsPeerRoomByBillboardId(long billboardId);
    BillboardRecord createBillboard(BillboardRecord billboardRecord);
    List<BillboardRecord> getAllBillboards();
    BillboardRecord getBillboardById(long billboardId);
    BillboardRecord getBillboardAvailableToday();
    BillboardRecord updateBillboard(long billboardId, BillboardRecord billboardRecord);
    void deleteBillboardById(long billboardId);
}
