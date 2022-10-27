package id.fahrizal.billreminder.data.repository.source.local

import id.fahrizal.billreminder.data.repository.source.BillEntityData
import id.fahrizal.billreminder.domain.model.Bill
import timber.log.Timber
import javax.inject.Inject

class LocalBillEntityData @Inject constructor() : BillEntityData {

    override suspend fun save(bill: Bill) {
        //TODO save to local DB
        Timber.d("fahrizal save to db will do later")
    }

    override suspend fun get(): List<Bill> {
        //Hardcode temporary
        val bills = ArrayList<Bill>()
        bills.add(Bill(1, "electricity", 500_000.0, 2))
        bills.add(Bill(2, "car", 2_000_000.0, 26))
        bills.add(Bill(2, "credit card", 500_000.0, 25))
        return bills
    }
}