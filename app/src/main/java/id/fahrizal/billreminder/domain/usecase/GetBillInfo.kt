package id.fahrizal.billreminder.domain.usecase

import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.data.repository.BillRepository
import javax.inject.Inject

class GetBillInfo @Inject constructor(
    private val billRepository: BillRepository
) {

    suspend operator fun invoke(billId: Long? = null): List<BillInfo> {
        return billRepository.getBillInfoList(billId)
    }
}