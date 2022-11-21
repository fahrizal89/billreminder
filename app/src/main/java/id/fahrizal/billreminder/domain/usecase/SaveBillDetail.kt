package id.fahrizal.billreminder.domain.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import id.fahrizal.billreminder.data.model.BillDetail
import id.fahrizal.billreminder.data.repository.BillRepository
import javax.inject.Inject

class SaveBillDetail @Inject constructor(
    @ApplicationContext private val context: Context,
    private val billRepository: BillRepository
) {

    suspend operator fun invoke(billDetails: List<BillDetail>) {
        billRepository.save(billDetails)
    }
}