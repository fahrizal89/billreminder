package id.fahrizal.billreminder.data.repository

import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.BillDetail
import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.data.repository.source.BillEntityData
import id.fahrizal.billreminder.data.repository.source.ReminderEntityData
import javax.inject.Inject

class BillEntityRepository @Inject constructor(
    private val billEntityData: BillEntityData,
    private val reminderEntityData: ReminderEntityData,
) : BillRepository {

    override suspend fun save(bill: Bill): Long {
        return billEntityData.save(bill)
    }

    override suspend fun getBills(billId: Long?): List<Bill> {
        return billEntityData.get(billId)
    }

    override suspend fun save(billDetails: List<BillDetail>) {
        val existingBillDetails = reminderEntityData.getBillDetails(billDetails[0].billId)

        //remove existing bill details and their notification scheduler when exist
        if (existingBillDetails.isNotEmpty()) {
            delete(existingBillDetails)
            removeReminder(existingBillDetails)
        }

        //save to db
        reminderEntityData.saveBillDetails(billDetails)
    }

    override suspend fun getBillDetails(billId: Long): List<BillDetail> {
        return reminderEntityData.getBillDetails()
    }

    override suspend fun getBillInfoList(billId: Long?): List<BillInfo> {
        return billEntityData.getBillInfoList(billId)
    }

    override suspend fun delete(billDetails: List<BillDetail>) {
        reminderEntityData.delete(billDetails)
    }

    override suspend fun saveReminder(billDetails: List<BillDetail>) {
        return reminderEntityData.setReminderNotification(billDetails)
    }

    private suspend fun removeReminder(billDetails: List<BillDetail>) {
        reminderEntityData.removeReminderNotification(billDetails)
    }
}