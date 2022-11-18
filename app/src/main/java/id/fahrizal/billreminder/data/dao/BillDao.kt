package id.fahrizal.billreminder.data.dao

import androidx.room.*
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.BillInfo

@Dao
interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bill: Bill): Long

    @Query("SELECT * FROM bill WHERE id LIKE :billId")
    fun getBills(billId: String): List<Bill>

    @Delete
    fun delete(bill: Bill)

    @Query("select bill_detail.billId, bill_detail.id as billDetailId, bill.name, bill.amount, bill_detail.message, bill_detail.notifDate, bill_detail.isPaid, bill.dayInMonth from bill, bill_detail " +
        "where bill.id = bill_detail.billId and notifDate < :maxDate and billId like :billId")
    fun getBillInfo(maxDate:Long, billId: String): List<BillInfo>
}