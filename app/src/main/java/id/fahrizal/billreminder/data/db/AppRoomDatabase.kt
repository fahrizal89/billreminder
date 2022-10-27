package id.fahrizal.billreminder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.fahrizal.billreminder.data.dao.BillDao
import id.fahrizal.billreminder.data.model.Bill

@Database(
    version = 1,
    entities = [Bill::class],
    exportSchema = true
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun billDao(): BillDao
}