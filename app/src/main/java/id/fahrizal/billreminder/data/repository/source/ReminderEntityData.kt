package id.fahrizal.billreminder.data.repository.source

import id.fahrizal.billreminder.data.model.BillDetail

interface ReminderEntityData {

    suspend fun saveBillDetails(billDetails: List<BillDetail>)
    suspend fun getBillDetails(billId:Long?=null): List<BillDetail>
    suspend fun delete(billDetails: List<BillDetail>)
    suspend fun setReminderNotification(billDetails: List<BillDetail>)
    suspend fun removeReminderNotification(billDetails: List<BillDetail>)
}