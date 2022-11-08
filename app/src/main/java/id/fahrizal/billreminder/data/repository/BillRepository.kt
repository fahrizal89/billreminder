package id.fahrizal.billreminder.data.repository

import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.Reminder

interface BillRepository {

    suspend fun save(bill: Bill) : Long
    suspend fun get(billId:Long?): List<Bill>
    suspend fun saveReminders(reminders: List<Reminder>)
    suspend fun getReminders() : List<Reminder>
}