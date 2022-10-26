package id.fahrizal.billreminder.domain.model

data class Bill(
    var id: Long? = null,
    var name: String = "",
    var amount: Double = 0.0,
    var reminderDate: Int = 1
)