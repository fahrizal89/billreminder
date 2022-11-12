package id.fahrizal.billreminder.ui.input

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.ui.input.BillInputViewModel.BillInputUiState
import id.fahrizal.billreminder.ui.theme.BillReminderTheme
import id.fahrizal.billreminder.ui.theme.Green40

@Composable
fun BillInputScreen(billInputViewModel: BillInputViewModel = viewModel()) {
    val activity: Activity = LocalContext.current as Activity
    val bill: Bill by remember { mutableStateOf(Bill()) }
    BillReminderTheme {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .animateContentSize()
        ) {
            val state = billInputViewModel.uiState.collectAsState().value
            when (state) {
                is BillInputUiState.Create -> {
                    InputForm(bill = bill, titleResId = R.string.add_new_bill)
                    WideButton(R.string.save) { billInputViewModel.save(bill) }
                }

                is BillInputUiState.Read -> {
                    InputForm(state.bill, false)
                    WideButton(R.string.edit) { billInputViewModel.edit(state.bill) }
                }

                is BillInputUiState.Edit -> {
                    InputForm(bill = state.bill, titleResId = R.string.add_new_bill)
                    WideButton(R.string.save) { billInputViewModel.save(state.bill) }
                }

                is BillInputUiState.Delete -> {
                    InputForm(
                        bill = state.bill,
                        editable = false,
                        titleResId = R.string.delete_bill
                    )
                    WideButton(R.string.delete) { billInputViewModel.save(state.bill) }
                }

                is BillInputUiState.Error -> {
                    InputForm(bill)
                    WideButton(R.string.save) { billInputViewModel.save(bill) }
                }

                is BillInputUiState.Loading -> {
                    InputForm(bill)
                    WideButton(R.string.save) { billInputViewModel.save(bill) }
                }

                is BillInputUiState.Finish -> activity.finish()
            }
        }
    }
}

@Composable
fun InputForm(bill: Bill, editable: Boolean = true, titleResId: Int = R.string.bill) {
    var dateInMounth: Int by remember { mutableStateOf(bill.dayInMonth) }
    Text(
        text = stringResource(titleResId),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        style = MaterialTheme.typography.h5,
    )

    InputTextField(
        labelResource = R.string.bill_name,
        hintResource = R.string.bill_name_hint,
        text = bill.name,
        enabled = editable
    ) { billName ->
        bill.name = billName
    }

    InputTextField(
        labelResource = R.string.amount,
        hintResource = R.string.amount_hint,
        text = if (bill.amount == 0.0) "" else bill.amount.toInt().toString(),
        singleLine = true,
        keyboardType = KeyboardType.Number,
        enabled = editable
    ) { inputedAmount ->
        if (inputedAmount.isNotEmpty() && inputedAmount.isDigitsOnly()) {
            bill.amount = inputedAmount.toDouble()
        }
    }

    Text(
        text = stringResource(id = R.string.reminder_date_label),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )

    NumberSelector(
        defaultValue = bill.dayInMonth,
        enabled = editable,
        onItemClick = { index ->
            bill.dayInMonth = index + 1
            dateInMounth = bill.dayInMonth
        })

    TextInfo(
        text = stringResource(id = R.string.bill_reminder_info, dateInMounth)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NumberSelector(
    defaultValue: Int = Bill.DEFAULT_DAY_IN_MONTH,
    enabled: Boolean = true,
    onItemClick: (index: Int) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var currentNumber by remember { mutableStateOf(defaultValue) }
    val keyboardController = LocalSoftwareKeyboardController.current

    if (isExpanded) {
        AnimatedVisibility(visible = isExpanded) {
            DayInMonthGrid(onItemClick = { index ->
                isExpanded = false
                currentNumber = index + 1
                onItemClick(index)
            })
        }

    } else {
        Button(enabled = enabled, onClick = {
            keyboardController?.hide()
            isExpanded = true
        }, modifier = Modifier.padding(8.dp)) {
            Text(text = currentNumber.toString())
        }
    }

}

@Composable
fun InputTextField(
    labelResource: Int,
    hintResource: Int,
    text: String = "",
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp),
    textCallback: (String) -> Unit = {}
) {
    var textField by remember { mutableStateOf(TextFieldValue(text)) }
    val keyboardOptions: KeyboardOptions = if (singleLine) {
        KeyboardOptions(imeAction = ImeAction.Next, keyboardType = keyboardType)
    } else {
        KeyboardOptions(imeAction = ImeAction.Default, keyboardType = keyboardType)
    }

    OutlinedTextField(
        value = textField,
        onValueChange = {
            textField = it
            textCallback(it.text)
        },
        label = { Text(stringResource(labelResource)) },
        placeholder = { Text(stringResource(hintResource)) },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        modifier = modifier
    )
}

@Composable
fun DayInMonthGrid(
    onItemClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier.padding(8.dp)
) {
    val dateList by remember {
        val list = ArrayList<String>()
        for (i: Int in 1..31) list.add(i.toString())
        mutableStateOf(list)
    }

    DataGrid(dateList, onItemClick, modifier)
}

@Composable
fun DataGrid(
    labels: ArrayList<String> = ArrayList(),
    onItemClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier.padding(8.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(labels.size) { i ->
            Button(onClick = {
                onItemClick(i)
            }) {
                Text(text = labels[i])
            }
        }
    }
}

@Composable
fun TextInfo(text: String) {
    Text(
        text = text,
        color = Green40,
        fontSize = MaterialTheme.typography.subtitle1.fontSize,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    )
}

@Composable
fun WideButton(resId: Int, onClick: () -> Unit) {
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
            Text(text = stringResource(id = resId))
        }
    }
}