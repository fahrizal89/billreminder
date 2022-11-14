package id.fahrizal.billreminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bill_detail")
data class BillDetail(
    val billId: Long,
    val message: String,
    val paymentDate: Long,
    @PrimaryKey val id: Int? = null
)