package id.fahrizal.billreminder.data.repository.source.local

import id.fahrizal.billreminder.data.model.Reminder
import id.fahrizal.billreminder.data.repository.source.ReminderEntityData
import javax.inject.Inject

class LocalReminderEntityData @Inject constructor(): ReminderEntityData{

    override suspend fun saveReminders(reminders: List<Reminder>) {

    }

    override suspend fun getReminders(): List<Reminder> {
        TODO("Not yet implemented")
    }
}