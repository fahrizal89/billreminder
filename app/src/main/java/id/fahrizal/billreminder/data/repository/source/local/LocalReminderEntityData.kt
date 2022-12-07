package id.fahrizal.billreminder.data.repository.source.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.dao.BillDao
import id.fahrizal.billreminder.data.dao.BillDetailDao
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.BillDetail
import id.fahrizal.billreminder.data.repository.source.ReminderEntityData
import id.fahrizal.billreminder.scheduler.SchedulerManager
import id.fahrizal.billreminder.scheduler.util.DateUtil
import timber.log.Timber
import javax.inject.Inject

class LocalReminderEntityData @Inject constructor(
    @ApplicationContext private val context: Context,
    private val billDetailDao: BillDetailDao,
    private val billDao: BillDao
) : ReminderEntityData {

    override suspend fun saveBillDetails(billDetails: List<BillDetail>) {
        return billDetailDao.insert(billDetails)
    }

    override suspend fun delete(bill: Bill) {
        billDao.delete(bill)
    }

    override suspend fun delete(billDetails: List<BillDetail>) {
        billDetailDao.delete(billDetails)
    }

    override suspend fun getBillDetails(billId: Long?): List<BillDetail> {
        val billIdParam = billId?.toString() ?: "%"
        return billDetailDao.get(billIdParam)
    }

    override suspend fun setReminderNotification(billDetails: List<BillDetail>) {
        val title = context.getString(R.string.app_name)

        for (reminder in billDetails) {
            Timber.d("fahrizal notify at " + DateUtil.getTimeInString(reminder.notifDate) + ", id: " + reminder.id)
            SchedulerManager.set(
                context,
                reminder.notifDate,
                title,
                reminder.message,
                reminder.id?.toInt() ?: 0
            )
        }
    }

    override suspend fun removeReminderNotification(billDetails: List<BillDetail>) {
        for (billDetail in billDetails) {
            if (billDetail.id != null) {
                SchedulerManager.cancel(context, billDetail.id.toInt())
            }
        }
    }
}