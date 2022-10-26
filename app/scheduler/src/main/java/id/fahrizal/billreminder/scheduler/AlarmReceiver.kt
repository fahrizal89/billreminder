package id.fahrizal.billreminder.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            handleAlaramData(context, intent)
        }
    }

    private fun handleAlaramData(context: Context?, intent: Intent) {
        context?.let {
            val title = intent.getStringExtra("title").orEmpty()
            val content = intent.getStringExtra("content").orEmpty()
            Notification.createNotificationChannel(context = it)
            Notification.createNotification(
                context = it,
                title = title,
                description = content,
            )
        }
    }
}