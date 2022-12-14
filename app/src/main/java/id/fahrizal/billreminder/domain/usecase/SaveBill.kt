package id.fahrizal.billreminder.domain.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.BillDetail
import id.fahrizal.billreminder.data.repository.BillRepository
import id.fahrizal.billreminder.scheduler.util.DateUtil
import id.fahrizal.billreminder.util.CurrencyUtil
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class SaveBill @Inject constructor(
    @ApplicationContext private val context: Context,
    private val billRepository: BillRepository
) {

    suspend operator fun invoke(bill: Bill) {
        //save parent bill
        val id = billRepository.save(bill)

        //save bill details
        val billDetails = generateBillDetails(id, bill)
        billRepository.save(billDetails)

        //save reminder
        val savedBillDetails = billRepository.getBillDetails(id)
        billRepository.saveReminder(savedBillDetails)
    }

    private fun generateBillDetails(
        billId: Long,
        bill: Bill,
        year: Int = 1
    ): ArrayList<BillDetail> {
        val billDetails = ArrayList<BillDetail>()
        val firstReminder =
            DateUtil.getDate(NOTIFY_HOUR, NOTIFY_MINUTE, NOTIFY_SECOND, bill.dayInMonth)
        val currentTime = Date().time
        for (i: Int in 0 until (year * 12)) {
            val reminderTime = DateUtil.plusMonth(firstReminder.time, i)
            val body = context.getString(
                R.string.bill_notification_message,
                bill.name + " " + CurrencyUtil.getRupiahAmount(bill.amount)
            )
            Timber.d("Save BillDetail at " + DateUtil.getTimeInString(reminderTime))
            val billDetail = BillDetail(billId, body, reminderTime)
            if (DateUtil.plusDay(currentTime, -1) < reminderTime) {
                billDetails.add(billDetail)
            }
        }

        return billDetails
    }

    companion object {

        private const val NOTIFY_HOUR = 9
        private const val NOTIFY_MINUTE = 0
        private const val NOTIFY_SECOND = 0
    }
}