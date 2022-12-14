package id.fahrizal.billreminder.ui.input

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.Bill
import id.fahrizal.billreminder.ui.input.BillInputViewModel.BillInputUiState
import id.fahrizal.billreminder.ui.theme.BillReminderTheme
import id.fahrizal.billreminder.util.CurrencyUtil

@Composable
fun BillInputScreen(billInputViewModel: BillInputViewModel = viewModel()) {
    val activity: Activity = LocalContext.current as Activity
    val bill: Bill by remember { mutableStateOf(Bill()) }
    BillReminderTheme {
        Column {
            val state = billInputViewModel.uiState.collectAsState().value
            when (state) {
                is BillInputUiState.Create -> {
                    Header(titleResId = R.string.add_new_bill)
                    InputForm(bill = bill)
                    WideButton(R.string.save) { billInputViewModel.save(bill) }
                }

                is BillInputUiState.Read -> {
                    Header()
                    BillDetailScreen(billInputViewModel, state.billInfo)
                }

                is BillInputUiState.Edit -> {
                    Header(titleResId = R.string.add_new_bill)
                    InputForm(bill = state.bill)
                    WideButton(R.string.save) { billInputViewModel.save(state.bill) }
                    WideOutlinedButton(resId = R.string.delete, fontColor = Color.Red) {
                        billInputViewModel.delete(state.bill)
                    }
                }

                is BillInputUiState.Delete -> {
                    Header(titleResId = R.string.delete_bill)
                    InputForm(bill = state.bill, editable = false)
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

                is BillInputUiState.Finish -> {
                    state.notifString?.let { notifString ->
                        val words = stringResource(notifString, state.varArgs.orEmpty())
                        Toast.makeText(activity, words, Toast.LENGTH_SHORT).show()
                    }
                    activity.finish()
                }
            }
        }
    }
}

@Composable
fun Header(titleResId: Int = R.string.bill) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(titleResId),
            fontSize = MaterialTheme.typography.h6.fontSize,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun InputForm(bill: Bill = Bill(), editable: Boolean = true) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .animateContentSize()
    ) {
        InputTextField(
            labelResource = R.string.bill_name,
            hintResource = R.string.bill_name_hint,
            text = bill.name,
            enabled = editable
        ) { billName ->
            bill.name = billName
        }

        CurrencyTextField(
            labelResource = R.string.estimation_amount,
            hintResource = R.string.amount_hint,
            text = if (bill.amount == 0.0) "" else bill.amount.toInt().toString(),
            singleLine = true,
            keyboardType = KeyboardType.Number,
            enabled = editable
        ) { inputedAmount ->
            bill.amount = CurrencyUtil.getAmount(inputedAmount)
        }

        Text(
            text = stringResource(id = R.string.reminder_date_label),
            modifier = Modifier.padding(4.dp, 8.dp, 4.dp, 4.dp),
            color = MaterialTheme.colors.primary
        )

        NumberSelector(
            defaultValue = bill.dayInMonth,
            enabled = editable,
            onItemClick = { index ->
                if (index >= 0) {
                    bill.dayInMonth = index + 1
                } else {
                    bill.dayInMonth = bill.dayInMonth
                }
            })
        Text(
            text = stringResource(id = R.string.reccurring_every_month),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            color = Color.Gray,
            fontSize = MaterialTheme.typography.caption.fontSize,
        )
    }
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
            }, currentSelectionIndex = currentNumber - 1)
        }
    } else {
        Row {
            Text(
                text = stringResource(id = R.string.date_day),
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterVertically)
            )

            Button(
                enabled = enabled,
                onClick = {
                    keyboardController?.hide()
                    isExpanded = true
                },
                modifier = Modifier.padding(4.dp, 2.dp, 4.dp, 2.dp)
            ) {
                Text(text = currentNumber.toString())
            }
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

    Text(
        text = stringResource(labelResource),
        modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
        color = MaterialTheme.colors.primary
    )
    Column {
        OutlinedTextField(
            value = textField,
            onValueChange = {
                textField = it
                textCallback(it.text)
            },
            placeholder = { Text(stringResource(hintResource)) },
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            enabled = enabled,
            modifier = modifier
        )
    }
}

@Composable
fun CurrencyTextField(
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

    Text(
        text = stringResource(labelResource),
        modifier = Modifier.padding(4.dp, 4.dp, 0.dp, 0.dp),
        color = MaterialTheme.colors.primary
    )
    OutlinedTextField(
        value = textField,
        onValueChange = {
            val idx = if (it.text == "") 0 else it.text.length + 1
            textField =
                it.copy(CurrencyUtil.getAmountStr(CurrencyUtil.getAmount(it.text)), TextRange(idx))
            textCallback(it.text)

        },
        placeholder = { Text(stringResource(hintResource)) },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        leadingIcon = { Text(text = "Rp") },
        modifier = modifier
    )
}

@Composable
fun DayInMonthGrid(
    onItemClick: (index: Int) -> Unit,
    currentSelectionIndex: Int = 24
) {
    val dateList by remember {
        val list = ArrayList<String>()
        for (i: Int in 1..31) list.add(i.toString())
        mutableStateOf(list)
    }

    DataGrid(dateList, onItemClick, currentSelectionIndex)
}

@Composable
fun DataGrid(
    labels: ArrayList<String> = ArrayList(),
    onItemClick: (index: Int) -> Unit,
    currentSelectionIndex: Int = 0
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(labels.size) { i ->
            if (i == currentSelectionIndex) {
                Button(onClick = {
                    onItemClick(i)
                }) {
                    Text(text = labels[i])
                }
            } else {
                OutlinedButton(onClick = {
                    onItemClick(i)
                }) {
                    Text(text = labels[i])
                }
            }

        }
    }
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
                .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = resId))
        }
    }
}

@Composable
fun WideOutlinedButton(resId: Int, fontColor: Color = Color.Unspecified, onClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.Bottom) {
        OutlinedButton(
            onClick,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 5.dp,
                pressedElevation = 10.dp,
                disabledElevation = 0.dp
            ),
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = resId), color = fontColor)
        }
    }
}