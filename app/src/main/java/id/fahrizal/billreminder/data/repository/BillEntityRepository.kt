package id.fahrizal.billreminder.data.repository

import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.BillDetail
import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.data.repository.source.BillEntityData
import id.fahrizal.billreminder.data.repository.source.ReminderEntityData
import javax.inject.Inject

class BillEntityRepository @Inject constructor(
    private val billEntityData: BillEntityData,
    private val reminderEntityData: ReminderEntityData
) : BillRepository {

    override suspend fun save(bill: Bill): Long {
        return billEntityData.save(bill)
    }

    override suspend fun getBills(billId: Long?): List<Bill> {
        return billEntityData.get(billId)
    }

    override suspend fun save(billDetails: List<BillDetail>) {
        reminderEntityData.saveBillDetails(billDetails)
        val savedReminder = reminderEntityData.getBillDetails(billDetails[0].billId.toInt())
        reminderEntityData.setReminderNotification(savedReminder)
    }

    override suspend fun getBillDetails(billId: Long): List<BillDetail> {
        return reminderEntityData.getBillDetails()
    }

    override suspend fun getUnpaidBill(): List<BillInfo> {
        return billEntityData.getUnpaidBill()
    }
}