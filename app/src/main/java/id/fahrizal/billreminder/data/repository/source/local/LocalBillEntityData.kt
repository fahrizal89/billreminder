package id.fahrizal.billreminder.data.repository.source.local

import id.fahrizal.billreminder.data.dao.BillDao
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.data.repository.source.BillEntityData
import id.fahrizal.billreminder.scheduler.util.DateUtil
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class LocalBillEntityData @Inject constructor(
    private val billDao: BillDao
) : BillEntityData {

    override suspend fun save(bill: Bill): Long {
        return billDao.insert(bill)
    }

    override suspend fun get(billId: Long?): List<Bill> {
        val billParam: String = billId?.toString() ?: "%"
        return billDao.getBills(billParam)
    }

    override suspend fun getBillInfoList(billId: Long?): List<BillInfo> {
        val billIdParam = billId?.toString() ?: "%"
        return billDao.getBillInfo(billIdParam)
    }
}