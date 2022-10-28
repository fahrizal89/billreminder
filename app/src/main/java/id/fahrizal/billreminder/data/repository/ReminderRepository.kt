package id.fahrizal.billreminder.data.repository

import id.fahrizal.billreminder.data.model.Reminder

interface ReminderRepository {

    suspend fun save(reminders: List<Reminder>)
    suspend fun get(): List<Reminder>
}