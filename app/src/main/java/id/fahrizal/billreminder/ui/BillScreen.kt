package id.fahrizal.billreminder.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.ui.theme.BillReminderTheme

@Composable
fun BillScreen() {
    BillReminderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            AddButton {

            }
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