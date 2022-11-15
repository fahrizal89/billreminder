package id.fahrizal.billreminder.data.model

data class BillInfo(
    val billId: Long,
    val name: String,
    val amount: Double,
    val message: String,
    val notifDate: Long,
    val isPaid: Boolean
)