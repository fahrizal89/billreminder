package id.fahrizal.billreminder.data.model

data class BillInfo(
    val billId: Long = 0,
    val billDetailId:Long = 0,
    val name: String = "",
    val amount: Double = 0.0,
    val message: String = "",
    val notifDate: Long = 0,
    val isPaid: Boolean = false,
    var dayInMonth: Int = 0
)