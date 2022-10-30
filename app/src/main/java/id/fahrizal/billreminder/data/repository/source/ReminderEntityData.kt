package id.fahrizal.billreminder.data.repository.source

import id.fahrizal.billreminder.data.model.Reminder

interface ReminderEntityData {

    suspend fun saveReminders(reminders: List<Reminder>)
    suspend fun getReminders(billId:Int?=null): List<Reminder>
    suspend fun setReminderNotification(reminders: List<Reminder>)
}