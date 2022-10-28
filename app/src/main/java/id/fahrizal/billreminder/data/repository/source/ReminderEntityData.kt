package id.fahrizal.billreminder.data.repository.source

import id.fahrizal.billreminder.data.model.Reminder

interface ReminderEntityData {

    suspend fun saveReminders(reminders: List<Reminder>)
    suspend fun getReminders(): List<Reminder>
}