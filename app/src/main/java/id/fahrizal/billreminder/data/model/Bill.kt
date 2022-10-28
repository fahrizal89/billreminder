package id.fahrizal.billreminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bill")
data class Bill(
    @PrimaryKey var id: Long? = null,
    var name: String = "",
    var amount: Double = 0.0,
    var dayInMonth: Int = DEFAULT_DAY_IN_MONTH
){
    companion object{
        const val DEFAULT_DAY_IN_MONTH = 25
    }
}