package id.fahrizal.billreminder.ui

import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.ui.input.BillInputActivity
import id.fahrizal.billreminder.ui.theme.BillReminderTheme

@Composable
fun BillScreen(mainViewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    BillReminderTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val state = mainViewModel.uiState.collectAsState().value
            when (state) {
                is MainViewModel.MainUiState.Loaded -> BillList(state.bills)
                is MainViewModel.MainUiState.Loading -> BillList()
                is MainViewModel.MainUiState.Error -> ShowToatError(state.stringRes)
            }

            AddButton {
                val intent = Intent(context, BillInputActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}

@Composable
fun BillList(bills: List<Bill> = ArrayList(), modifier: Modifier = Modifier.padding(8.dp)) {
    Divider()
    LazyColumn(modifier = modifier) {
        items(bills) { bill ->
            Row {
                Text(
                    text = bill.reminderDate.toString(),
                    modifier = Modifier.padding(horizontal = 14.dp)
                )
                Text(text = bill.name, modifier = Modifier.padding(horizontal = 14.dp))
                Text(text = bill.amount.toString(), modifier = Modifier.padding(horizontal = 14.dp))
            }
            Divider()
        }
    }
}

@Composable
fun AddButton(onClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.Bottom) {
        Button(
            onClick,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 5.dp,
                pressedElevation = 10.dp,
                disabledElevation = 0.dp
            ),
            modifier = Modifier
                .padding(8.dp, 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.add))
        }
    }
}

@Composable
fun ShowToatError(@StringRes resId: Int) {
    Toast.makeText(LocalContext.current, stringResource(id = resId), Toast.LENGTH_SHORT).show()
}