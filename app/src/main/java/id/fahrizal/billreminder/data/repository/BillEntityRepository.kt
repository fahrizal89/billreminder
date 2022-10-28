package id.fahrizal.billreminder.data.repository

import id.fahrizal.billreminder.data.repository.source.BillEntityData
import id.fahrizal.billreminder.data.model.Bill
import javax.inject.Inject

class BillEntityRepository @Inject constructor(
    private val billEntityData: BillEntityData
) : BillRepository {

    override suspend fun save(bill: Bill)  : Long{
        return billEntityData.save(bill)
    }

    override suspend fun get(): List<Bill> {
        return billEntityData.get()
    }

}