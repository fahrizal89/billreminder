package id.fahrizal.billreminder.data.repository

import id.fahrizal.billreminder.data.repository.source.BillEntityData
import id.fahrizal.billreminder.domain.model.Bill
import javax.inject.Inject

class BillEntityRepository @Inject constructor(
    private val billEntityData: BillEntityData
) : BillRepository {

    override suspend fun save(bills: List<Bill>) {
        billEntityData.save(bills)
    }

    override suspend fun get(): List<Bill> {
        return billEntityData.get()
    }

}