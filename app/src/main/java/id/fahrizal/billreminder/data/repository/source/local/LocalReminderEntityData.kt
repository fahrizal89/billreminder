package id.fahrizal.billreminder.data.repository.source.local

import id.fahrizal.billreminder.data.dao.ReminderDao
import id.fahrizal.billreminder.data.model.Reminder
import id.fahrizal.billreminder.data.repository.source.ReminderEntityData
import javax.inject.Inject

class LocalReminderEntityData @Inject constructor(
    private val reminderDao: ReminderDao
): ReminderEntityData{

    override suspend fun saveReminders(reminders: List<Reminder>) {
        reminderDao.insert(reminders)
    }

    override suspend fun getReminders(): List<Reminder> {
        return reminderDao.get()
    }
}