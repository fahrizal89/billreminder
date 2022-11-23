package id.fahrizal.billreminder.util

import java.text.NumberFormat
import java.util.*

object CurrencyUtil {

    fun getRupiahAmount(amount: Double): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("IDR")

        return format.format(amount)
            .replace("Rp", "Rp ")
            .replace("IDR", "Rp ")
    }
}