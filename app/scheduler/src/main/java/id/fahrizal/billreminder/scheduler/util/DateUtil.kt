package id.fahrizal.billreminder.scheduler.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun getDate(
        hour: Int,
        minute: Int = 0,
        second: Int = 0,
        day: Int? = null,
        month: Int? = null
    ): Date {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = second

        if (day != null) {
            calendar[Calendar.DAY_OF_MONTH] = day
        }

        if (month != null) {
            calendar[Calendar.MONTH] = month
        }

        return calendar.time
    }

    fun plusMonth(time: Long, plusMonth: Int): Long {
        Calendar.getInstance().apply {
            timeInMillis = time
            set(Calendar.MONTH, getMonth(getTime()) + plusMonth)
            return this.time.time
        }
    }

    fun getMonth(date: Date): Int = SimpleDateFormat("MM", Locale.US).format(date).toInt() - 1

    fun getTimeInString(time: Long) : String {
        return Date(time).toString()
    }
}