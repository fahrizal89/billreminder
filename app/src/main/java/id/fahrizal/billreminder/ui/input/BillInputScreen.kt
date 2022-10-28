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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.Bill

@Composable
fun BillInputScreen(billInputViewModel: BillInputViewModel = viewModel()) {
    val context = LocalContext.current
    val bill: Bill by remember { mutableStateOf(Bill()) }

    Column(
        modifier = Modifier
            .padding(4.dp)
            .animateContentSize()
    ) {
        Text(
            text = stringResource(id = R.string.add_new_bill),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.h5,
        )

        InputTextField(
            labelResource = R.string.bill_name,
            hintResource = R.string.bill_name_hint,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) { billName ->
            bill.name = billName
        }

        InputTextField(
            labelResource = R.string.amount,
            hintResource = R.string.amount_hint,
            singleLine = true,
            keyboardType = KeyboardType.Number,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) { inputedAmount ->
            if (inputedAmount.isNotEmpty()) {
                bill.amount = inputedAmount.toDouble()
            }
        }

        Text(
            text = stringResource(id = R.string.reminder_date_label),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )

        NumberSelector(onItemClick = { index ->
            bill.reminderDate = index + 1
        })

        SaveButton {
            billInputViewModel.save(bill)
            (context as Activity).finish()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NumberSelector(onItemClick: (index: Int) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var currentNumber by remember { mutableStateOf(Bill.DEFAULT_DAY_IN_MONTH) }
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
        Button(onClick = {
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
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
    textCallback: (String) -> Unit = {}
) {
    var textField by remember { mutableStateOf(TextFieldValue(text)) }
    val keyboardOptions: KeyboardOptions = if (singleLine) {
        KeyboardOptions(imeAction = ImeAction.Next, keyboardType = keyboardType)
    } else {
        KeyboardOptions(imeAction = ImeAction.Default, keyboardType = keyboardType)
    }

    TextField(
        value = textField,
        onValueChange = {
            textField = it
            textCallback(it.text)
        },
        label = { Text(stringResource(labelResource)) },
        placeholder = { Text(stringResource(hintResource)) },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
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
        for (i: Int in 1..29) list.add(i.toString())
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
fun SaveButton(onClick: () -> Unit) {
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
            Text(text = stringResource(id = R.string.save))
        }
    }
}