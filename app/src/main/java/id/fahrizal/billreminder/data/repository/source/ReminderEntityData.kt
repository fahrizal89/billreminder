package id.fahrizal.billreminder.data.repository.source

import id.fahrizal.billreminder.data.model.BillDetail

interface ReminderEntityData {

    suspend fun saveBillDetails(billDetails: List<BillDetail>)
    suspend fun getBillDetails(billId:Int?=null): List<BillDetail>
    suspend fun setReminderNotification(billDetails: List<BillDetail>)
}