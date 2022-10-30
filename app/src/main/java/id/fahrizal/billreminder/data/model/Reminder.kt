package id.fahrizal.billreminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder")
data class Reminder(
    val billId: Long,
    val message: String,
    val time: Long,
    @PrimaryKey val id: Int? = null
)