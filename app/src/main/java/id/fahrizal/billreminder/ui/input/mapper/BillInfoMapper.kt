package id.fahrizal.billreminder.ui.input.mapper

import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.BillDetail
import id.fahrizal.billreminder.data.model.BillInfo

object BillInfoMapper {

    fun BillInfo.toBill(): Bill {
        return Bill(billId, name, amount, dayInMonth)
    }

    fun BillInfo.toBillDetail(): BillDetail {
        return BillDetail(
            billId,
            message,
            notifDate,
            true,
            billDetailId
        )
    }
}