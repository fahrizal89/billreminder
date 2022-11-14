package id.fahrizal.billreminder.domain.usecase

import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.repository.BillRepository
import javax.inject.Inject

class GetBills @Inject constructor(
    private val billRepository: BillRepository
) {

    suspend operator fun invoke(billId: Long? = null): List<Bill> {
        return billRepository.getBills(billId)
    }
}