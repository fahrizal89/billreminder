package id.fahrizal.billreminder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.fahrizal.billreminder.data.dao.BillDao
import id.fahrizal.billreminder.data.dao.ReminderDao
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.Reminder

@Database(
    version = 1,
    entities = [Bill::class, Reminder::class],
    exportSchema = true
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun billDao(): BillDao
    abstract fun reminderDao(): ReminderDao
}