package id.fahrizal.billreminder.data.dao

import androidx.room.*
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.Reminder

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminders: List<Reminder>)

    @Query("SELECT * FROM reminder WHERE billId LIKE :billIdString")
    fun get(billIdString: String): List<Reminder>

    @Delete
    fun delete(reminders: List<Reminder>)
}