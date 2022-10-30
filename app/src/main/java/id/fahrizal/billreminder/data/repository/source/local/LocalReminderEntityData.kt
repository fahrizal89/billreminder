package id.fahrizal.billreminder.data.repository.source.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.dao.ReminderDao
import id.fahrizal.billreminder.data.model.Reminder
import id.fahrizal.billreminder.data.repository.source.ReminderEntityData
import id.fahrizal.billreminder.scheduler.SchedulerManager
import id.fahrizal.billreminder.scheduler.util.DateUtil
import timber.log.Timber
import javax.inject.Inject

class LocalReminderEntityData @Inject constructor(
    @ApplicationContext private val context: Context,
    private val reminderDao: ReminderDao
) : ReminderEntityData {

    override suspend fun saveReminders(reminders: List<Reminder>) {
        return reminderDao.insert(reminders)
    }

    override suspend fun getReminders(billId:Int?): List<Reminder> {
        val billIdParam = billId?.toString() ?: "%"
        return reminderDao.get(billIdParam)
    }

    override suspend fun setReminderNotification(reminders: List<Reminder>) {
        val title = context.getString(R.string.app_name)

        for (reminder in reminders) {
            Timber.d("fahrizal notify at " + DateUtil.getTimeInString(reminder.time) + ", id: "+ reminder.id)
            SchedulerManager.set(context, reminder.time, title, reminder.message, reminder.id ?: 0)
        }
    }
}