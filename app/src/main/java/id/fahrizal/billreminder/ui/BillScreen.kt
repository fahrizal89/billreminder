package id.fahrizal.billreminder.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.domain.model.Bill
import id.fahrizal.billreminder.ui.input.BillInputActivity
import id.fahrizal.billreminder.ui.theme.BillReminderTheme

@Composable
fun BillScreen() {
    val context = LocalContext.current
    BillReminderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            BillList()

            AddButton {
                val intent = Intent(context, BillInputActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}

@Composable
fun BillList(bills: ArrayList<Bill> = ArrayList(), modifier: Modifier = Modifier.padding(8.dp)) {
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