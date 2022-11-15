package id.fahrizal.billreminder.domain.usecase

import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.data.repository.BillRepository
import javax.inject.Inject

class GetUnpaidBillInfo @Inject constructor(
    private val billRepository: BillRepository
) {

    suspend operator fun invoke(): List<BillInfo> {
        return billRepository.getUnpaidBill()
    }
}