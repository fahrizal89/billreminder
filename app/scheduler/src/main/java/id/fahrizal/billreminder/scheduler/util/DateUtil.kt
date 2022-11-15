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

    fun plusDay(time: Long, day: Int): Long {
        Calendar.getInstance().apply {
            timeInMillis = time
            set(Calendar.MONTH, getMonth(getTime()))
            set(Calendar.DAY_OF_MONTH, getDay(Date(time)) + day)
            return this.time.time
        }
    }

    fun getLasyDayInMonth(date: Date): Long {
        Calendar.getInstance().apply {
            timeInMillis = plusMonth(date.time, 1)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 0)
            add(Calendar.DATE, -1)
            return this.time.time
        }
    }

    fun getMonth(date: Date): Int = SimpleDateFormat("MM", Locale.US).format(date).toInt() - 1

    fun getDay(date: Date): Int = SimpleDateFormat("dd", Locale.US).format(date).toInt()

    fun getYear(date: Date): Int = SimpleDateFormat("dd", Locale.US).format(date).toInt()

    fun getTimeInString(time: Long): String {
        return Date(time).toString()
    }

    fun getDateString(time: Long): String = SimpleDateFormat("dd/MM/yy", Locale.US).format(Date(time))
}