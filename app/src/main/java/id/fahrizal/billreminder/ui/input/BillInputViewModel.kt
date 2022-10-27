package id.fahrizal.billreminder.ui.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.domain.usecase.SaveBill
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillInputViewModel @Inject constructor(
    private val ioCoroutineDispatcher: CoroutineDispatcher,
    private val saveBill: SaveBill
) : ViewModel() {

    fun save(bill: Bill) {
        viewModelScope.launch(ioCoroutineDispatcher) {
            saveBill(bill)
        }
    }
}