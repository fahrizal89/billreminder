package id.fahrizal.billreminder.data.repository

import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.BillDetail
import id.fahrizal.billreminder.data.model.BillInfo

interface BillRepository {

    suspend fun save(bill: Bill): Long
    suspend fun getBills(billId: Long?): List<Bill>
    suspend fun save(billDetails: List<BillDetail>)
    suspend fun delete(bill: Bill)
    suspend fun delete(billDetails: List<BillDetail>)
    suspend fun getBillDetails(billId: Long): List<BillDetail>
    suspend fun getBillInfoList(billId: Long?): List<BillInfo>
    suspend fun saveReminder(billDetails: List<BillDetail>)
}