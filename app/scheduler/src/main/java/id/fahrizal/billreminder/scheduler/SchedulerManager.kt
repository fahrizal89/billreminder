package id.fahrizal.billreminder.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.ComponentActivity
import id.fahrizal.billreminder.scheduler.util.DateUtil
import timber.log.Timber

object SchedulerManager {

    const val TITLE = "title"
    const val CONTENT = "content"

    fun set(context: Context, time: Long, title: String, content: String, requestCode: Int = 1000) {
        if (!checkAlarmsAccess(context)) {
            Timber.e("you don't have permission to access alarm")
            return
        }

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(TITLE, title)
        intent.putExtra(CONTENT, content)
        val pendingIntent = PendingIntent.getBroadcast(
            context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun checkAlarmsAccess(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager?
        return alarmManager?.canScheduleExactAlarms() == true
    }
}