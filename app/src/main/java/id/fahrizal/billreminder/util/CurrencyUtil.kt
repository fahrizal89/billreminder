package id.fahrizal.billreminder.util

import java.text.NumberFormat
import java.util.*

object CurrencyUtil {

    fun getRupiahAmount(amount: Double): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("IDR")

        return format.format(amount)
            .replace("Rp", "Rp.")
            .replace("IDR", "Rp.")
    }

    fun getAmountStr(amount: Double?): String {
        if (amount == null) return ""

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("IDR")

        return format.format(amount)
            .replace("Rp", "")
            .replace("IDR", "")
    }

    fun getAmount(amountStr: String): Double {
        val amount = amountStr
            .replace(",", "")
            .replace(".", "")
            .toDoubleOrNull()

        return amount ?: 0.0
    }
}