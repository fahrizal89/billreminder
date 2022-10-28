package id.fahrizal.billreminder.domain.usecase

import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.Reminder
import id.fahrizal.billreminder.data.repository.BillRepository
import id.fahrizal.billreminder.scheduler.util.DateUtil
import timber.log.Timber
import javax.inject.Inject

class SaveBill @Inject constructor(
    private val billRepository: BillRepository
) {

    suspend operator fun invoke(bill: Bill) {
        val id = billRepository.save(bill)
        val reminders = generateReminderInYear(id, bill.dayInMonth)
        billRepository.saveReminders(reminders)
    }

    private fun generateReminderInYear(
        billId: Long,
        startDayOfMonth: Int,
        year: Int = 1
    ): ArrayList<Reminder> {
        val reminders = ArrayList<Reminder>()
        val firstReminder = DateUtil.getDate(8, 0, 0, startDayOfMonth)
        for (i: Int in 0 until (year * 12)) {
            val time = DateUtil.plusMonth(firstReminder.time, i)
            Timber.d("SaveBill at " + time)
            val reminder = Reminder(billId, time)
            reminders.add(reminder)
        }

        return reminders
    }
}