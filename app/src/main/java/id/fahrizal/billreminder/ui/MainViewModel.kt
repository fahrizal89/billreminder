package id.fahrizal.billreminder.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.domain.model.Bill
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    fun fetchBills() {
        val bills = ArrayList<Bill>()
        bills.add(Bill(1, "electricity", 500_000.0, 2))
        bills.add(Bill(2, "car", 2_000_000.0, 26))
        _uiState.value = MainUiState.Loaded(bills)
    }

    sealed class MainUiState {
        object Loading : MainUiState()
        class Loaded(val bills: List<Bill>) : MainUiState()
        class Error(@StringRes val stringRes: Int = R.string.error) : MainUiState()
    }
}