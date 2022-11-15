package id.fahrizal.billreminder.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.domain.usecase.GetBills
import id.fahrizal.billreminder.domain.usecase.GetUnpaidBillInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val ioCoroutineDispatcher: CoroutineDispatcher,
    private val getBills: GetBills,
    private val getUnpaidBillInfo: GetUnpaidBillInfo
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    fun fetchBills() {
        viewModelScope.launch(ioCoroutineDispatcher) {
            try {
                val unpaidBillInfo = getUnpaidBillInfo()
                _uiState.value = MainUiState.Loaded(unpaidBillInfo)
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.value = MainUiState.Error()
            }
        }
    }

    sealed class MainUiState {
        object Loading : MainUiState()
        class Loaded(val bills: List<BillInfo>) : MainUiState()
        class Error(@StringRes val stringRes: Int = R.string.error) : MainUiState()
    }
}