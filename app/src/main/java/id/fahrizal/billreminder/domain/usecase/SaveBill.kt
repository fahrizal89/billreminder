package id.fahrizal.billreminder.domain.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.Reminder
import id.fahrizal.billreminder.data.repository.BillRepository
import id.fahrizal.billreminder.scheduler.util.DateUtil
import id.fahrizal.billreminder.util.CurrencyUtil
import timber.log.Timber
import javax.inject.Inject

class SaveBill @Inject constructor(
    @ApplicationContext private val context: Context,
    private val billRepository: BillRepository
) {

    suspend operator fun invoke(bill: Bill) {
        val id = billRepository.save(bill)
        val reminders = generateReminderInYear(id, bill)
        billRepository.saveReminders(reminders)
    }

    private fun generateReminderInYear(
        billId: Long,
        bill: Bill,
        year: Int = 1
    ): ArrayList<Reminder> {
        val reminders = ArrayList<Reminder>()
        val firstReminder =
            DateUtil.getDate(NOTIFY_HOUR, NOTIFY_MINUTE, NOTIFY_SECOND, bill.dayInMonth)
        for (i: Int in 0 until (year * 12)) {
            val time = DateUtil.plusMonth(firstReminder.time, i)
            val body = context.getString(
                R.string.bill_notification_message,
                bill.name + " " + CurrencyUtil.getRupiahAmount(bill.amount)
            )
            Timber.d("fahrizal SaveBill at " + DateUtil.getTimeInString(time))
            val reminder = Reminder(billId, body, time)
            reminders.add(reminder)
        }

        return reminders
    }

    companion object {

        private const val NOTIFY_HOUR = 9
        private const val NOTIFY_MINUTE = 0
        private const val NOTIFY_SECOND = 0
    }
}