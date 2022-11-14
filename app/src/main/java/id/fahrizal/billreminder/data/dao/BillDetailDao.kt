package id.fahrizal.billreminder.data.dao

import androidx.room.*
import id.fahrizal.billreminder.data.model.BillDetail

@Dao
interface BillDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(billDetails: List<BillDetail>)

    @Query("SELECT * FROM bill_detail WHERE billId LIKE :billIdString")
    fun get(billIdString: String): List<BillDetail>

    @Delete
    fun delete(billDetails: List<BillDetail>)
}