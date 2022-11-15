package id.fahrizal.billreminder.ui

import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.scheduler.util.DateUtil
import id.fahrizal.billreminder.ui.input.BillInputActivity
import id.fahrizal.billreminder.ui.theme.BillReminderTheme
import id.fahrizal.billreminder.util.CurrencyUtil

@Composable
fun BillScreen(mainViewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    BillReminderTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val state = mainViewModel.uiState.collectAsState().value
            when (state) {
                is MainViewModel.MainUiState.Loaded -> BillTable(state.bills)
                is MainViewModel.MainUiState.Loading -> BillTable()
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
fun BillTable(bills: List<BillInfo> = ArrayList()) {
    Column {
        BillHeader()
        Divider(modifier = Modifier.padding(4.dp))
        BillList(bills)
    }
}

@Composable
fun BillList(bills: List<BillInfo> = ArrayList(), modifier: Modifier = Modifier.padding(8.dp)) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier
            .padding(bottom = 73.dp)
            .fillMaxWidth()
    ) {
        items(bills) { bill ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            val intent = BillInputActivity.createIntent(context, bill.billId)
                            context.startActivity(intent)
                        })
                    }
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = DateUtil.getDateString(bill.notifDate),
                        fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    )
                }
                Column(Modifier.weight(2f)) {
                    Text(text = bill.name, modifier = Modifier.padding(horizontal = 14.dp))
                }
                Column(Modifier.weight(2f)) {
                    Text(
                        text = CurrencyUtil.getRupiahAmount(bill.amount),
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )
                }
            }
            Divider()
        }
    }
}

@Composable
fun BillHeader() {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.date),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 14.dp),
            )
        }
        Column(Modifier.weight(2f)) {
            Text(
                text = stringResource(id = R.string.bill_name),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 14.dp)
            )
        }
        Column(Modifier.weight(2f)) {
            Text(
                text = stringResource(id = R.string.amount),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 14.dp)
            )
        }
    }
}

@Composable
fun AddButton(onClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.Bottom) {
        Divider()
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