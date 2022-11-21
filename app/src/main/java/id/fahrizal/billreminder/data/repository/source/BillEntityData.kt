package id.fahrizal.billreminder.data.repository.source

import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.BillInfo

interface BillEntityData {

    suspend fun save(bill: Bill): Long
    suspend fun get(billId: Long?): List<Bill>
    suspend fun getBillInfoList(billId: Long?): List<BillInfo>
}