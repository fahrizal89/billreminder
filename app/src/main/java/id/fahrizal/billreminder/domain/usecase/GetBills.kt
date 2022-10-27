package id.fahrizal.billreminder.domain.usecase

import id.fahrizal.billreminder.data.repository.BillRepository
import id.fahrizal.billreminder.data.model.Bill
import javax.inject.Inject

class GetBills @Inject constructor(
    private val billRepository: BillRepository
) {

    suspend operator fun invoke(): List<Bill> {
        return billRepository.get()
    }
}