package id.fahrizal.billreminder.ui.input

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.data.model.BillDetail
import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.domain.usecase.GetUnpaidBillInfo
import id.fahrizal.billreminder.domain.usecase.SaveBill
import id.fahrizal.billreminder.domain.usecase.SaveBillDetail
import id.fahrizal.billreminder.ui.input.mapper.BillInfoMapper.toBill
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
    private val getUnpaidBillInfo: GetUnpaidBillInfo,
    private val saveBillDetail: SaveBillDetail
) : ViewModel() {

    private val _uiState = MutableStateFlow<BillInputUiState>(BillInputUiState.Loading)
    val uiState: StateFlow<BillInputUiState> = _uiState

    init {
        val billId: Long? = savedStateHandle.get<Long>(BillInputActivity.BILL_ID)
        when (billId) {
            null -> _uiState.value = BillInputUiState.Create
            else -> setUiStateAsRead(billId)
        }
    }

    fun save(bill: Bill) {
        viewModelScope.launch(ioCoroutineDispatcher) {
            saveBill(bill)
            _uiState.value = BillInputUiState.Finish
        }
    }

    fun edit(billInfo: BillInfo) {
        viewModelScope.launch(ioCoroutineDispatcher) {
            _uiState.value = BillInputUiState.Edit(billInfo.toBill())
        }
    }

    fun save(billDetail: BillDetail) {
        viewModelScope.launch(ioCoroutineDispatcher) {
            val billDetails = ArrayList<BillDetail>().apply { add(billDetail) }
            saveBillDetail(billDetails)
            _uiState.value = BillInputUiState.Finish
        }
    }

    private fun setUiStateAsRead(billId: Long) {
        viewModelScope.launch(ioCoroutineDispatcher) {
            try {
                val billInfo = getUnpaidBillInfo(billId)[0]
                _uiState.value = BillInputUiState.Read(billInfo)
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = BillInputUiState.Error(R.string.error)
            }
        }
    }

    sealed class BillInputUiState {
        object Loading : BillInputUiState()
        object Create : BillInputUiState()
        class Read(val billInfo: BillInfo) : BillInputUiState()
        class Edit(val bill: Bill) : BillInputUiState()
        class Delete(val bill: Bill) : BillInputUiState()
        object Finish : BillInputUiState()
        class Error(@StringRes val stringRes: Int = R.string.error) : BillInputUiState()
    }
}