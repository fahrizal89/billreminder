package id.fahrizal.billreminder.ui.input

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.domain.usecase.GetBills
import id.fahrizal.billreminder.domain.usecase.GetUnpaidBillInfo
import id.fahrizal.billreminder.domain.usecase.SaveBill
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BillInputViewModel @Inject constructor(
    private val ioCoroutineDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    private val saveBill: SaveBill,
    private val getBills: GetBills,
    private val getUnpaidBillInfo: GetUnpaidBillInfo
) : ViewModel() {

    private val _uiState = MutableStateFlow<BillInputUiState>(BillInputUiState.Loading)
    val uiState: StateFlow<BillInputUiState> = _uiState

    init {
        val billId: Long? = savedStateHandle.get<Long>(BillInputActivity.BILL_ID)
        when (billId) {
            null -> _uiState.value = BillInputUiState.Create
            else -> setStateAsEdit(billId)
        }
    }

    fun save(bill: Bill) {
        viewModelScope.launch(ioCoroutineDispatcher) {
            saveBill(bill)
            _uiState.value = BillInputUiState.Finish
        }
    }

    fun edit(bill: Bill) {
        viewModelScope.launch(ioCoroutineDispatcher) {
            _uiState.value = BillInputUiState.Edit(bill)
        }
    }

    private fun setStateAsEdit(billId: Long) {
        viewModelScope.launch(ioCoroutineDispatcher) {
            try {
                val bill = getBills(billId)[0]
                _uiState.value = BillInputUiState.Read(bill)

                getUnpaidBillInfo.invoke()
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = BillInputUiState.Error(R.string.error)
            }
        }
    }

    sealed class BillInputUiState {
        object Loading : BillInputUiState()
        object Create : BillInputUiState()
        class Read(val bill: Bill) : BillInputUiState()
        class Edit(val bill: Bill) : BillInputUiState()
        class Delete(val bill: Bill) : BillInputUiState()
        object Finish : BillInputUiState()
        class Error(@StringRes val stringRes: Int = R.string.error) : BillInputUiState()
    }
}