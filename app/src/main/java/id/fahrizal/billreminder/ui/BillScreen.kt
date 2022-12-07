package id.fahrizal.billreminder.ui

import android.content.Context
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.scheduler.util.DateUtil
import id.fahrizal.billreminder.ui.input.BillInputActivity
import id.fahrizal.billreminder.ui.theme.BillReminderTheme
import id.fahrizal.billreminder.ui.theme.DarkGreen
import id.fahrizal.billreminder.ui.theme.LightGrey
import id.fahrizal.billreminder.util.CurrencyUtil
import java.util.*

@Composable
fun BillScreen(mainViewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    BillReminderTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val state = mainViewModel.uiState.collectAsState().value
            when (state) {
                is MainViewModel.MainUiState.Loaded -> BillPage(state.bills)
                is MainViewModel.MainUiState.Loading -> BillPage()
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
fun BillPage(bills: List<BillInfo> = ArrayList()) {
    Column {
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.bills),
                fontSize = MaterialTheme.typography.h6.fontSize,
                modifier = Modifier.padding(8.dp)
            )
        }
        BillList(bills)
    }
}

@Composable
fun BillList(
    bills: List<BillInfo> = ArrayList(),
    modifier: Modifier = Modifier
        .padding(bottom = 81.dp)
        .fillMaxWidth()
) {
    LazyColumn(modifier = modifier) {
        items(items = bills, key = { it.billId }) { bill ->
            BillItem(bill)
            Divider()
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