package id.fahrizal.billreminder.data.dao

import androidx.room.*
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.Reminder

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminders: List<Reminder>)

    @Query("SELECT * FROM reminder")
    fun get(): List<Reminder>

    @Delete
    fun delete(reminders: List<Reminder>)
}