package id.fahrizal.billreminder.data.dao

import androidx.room.*
import id.fahrizal.billreminder.data.model.Bill

@Dao
interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bill: Bill)

    @Query("SELECT * FROM bill")
    fun getBills(): List<Bill>

    @Delete
    fun delete(bill: Bill)
}