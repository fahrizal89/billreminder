package id.fahrizal.billreminder.scheduler.util

import java.util.*

object DateUtil {

    fun getDate(hour: Int, minute: Int, second: Int): Date {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = second

        return calendar.time
    }

    fun getFullDayInMillis(): Long {
        return 1000 * 60 * 60 * 24
    }
}