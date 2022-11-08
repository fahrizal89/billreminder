package id.fahrizal.billreminder.data.dao

import androidx.room.*
import id.fahrizal.billreminder.data.model.Bill

@Dao
interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bill: Bill): Long

    @Query("SELECT * FROM bill WHERE id LIKE :billId")
    fun getBills(billId: String): List<Bill>

    @Delete
    fun delete(bill: Bill)
}