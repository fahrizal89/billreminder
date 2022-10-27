package id.fahrizal.billreminder.data.repository.source.local

import id.fahrizal.billreminder.data.dao.BillDao
import id.fahrizal.billreminder.data.repository.source.BillEntityData
import id.fahrizal.billreminder.data.model.Bill
import timber.log.Timber
import javax.inject.Inject

class LocalBillEntityData @Inject constructor(
    private val billDao: BillDao
) : BillEntityData {

    override suspend fun save(bill: Bill) {
        billDao.insert(bill)
    }

    override suspend fun get(): List<Bill> {
        return billDao.getBills()
    }
}