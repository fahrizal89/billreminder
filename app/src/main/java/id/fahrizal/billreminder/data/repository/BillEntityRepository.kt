package id.fahrizal.billreminder.data.repository

import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.Reminder
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

    override suspend fun get(): List<Bill> {
        return billEntityData.get()
    }

    override suspend fun saveReminders(reminders: List<Reminder>) {
        reminderEntityData.saveReminders(reminders)
        val savedReminder = reminderEntityData.getReminders(reminders[0].billId.toInt())
        reminderEntityData.setReminderNotification(savedReminder)
    }

    override suspend fun getReminders(): List<Reminder> {
        return reminderEntityData.getReminders()
    }
}