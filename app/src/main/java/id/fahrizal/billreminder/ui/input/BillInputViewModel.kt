package id.fahrizal.billreminder.ui.input

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.fahrizal.billreminder.domain.model.Bill
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BillInputViewModel @Inject constructor(): ViewModel() {

    fun save(bill: Bill) {
        Timber.d("fahrizal bill"+ bill.name +", "+ bill.amount+", "+bill.reminderDate)
    }
}