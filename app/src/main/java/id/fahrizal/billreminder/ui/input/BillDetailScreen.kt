package id.fahrizal.billreminder.ui.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.scheduler.util.DateUtil
import id.fahrizal.billreminder.ui.input.mapper.BillInfoMapper.toBillDetail
import id.fahrizal.billreminder.util.CurrencyUtil

@Composable
fun BillDetailScreen(
    billInputViewModel: BillInputViewModel = viewModel(),
    billInfo: BillInfo = BillInfo()
) {
    Column {
        Text(
            text = stringResource(id = R.string.bill_name),
            color = Color.DarkGray,
            fontSize = MaterialTheme.typography.caption.fontSize,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )

        Text(
            text = billInfo.name,
            fontSize = MaterialTheme.typography.body1.fontSize,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )

        Divider(modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp))

        Text(
            text = stringResource(id = R.string.amount),
            color = Color.DarkGray,
            fontSize = MaterialTheme.typography.caption.fontSize,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )

        Text(
            text = CurrencyUtil.getRupiahAmount(billInfo.amount),
            fontSize = MaterialTheme.typography.body1.fontSize,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )

        Divider(modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp))

        Text(
            text = stringResource(id = R.string.paid_status),
            color = Color.DarkGray,
            fontSize = MaterialTheme.typography.caption.fontSize,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )

        Text(
            text = if (billInfo.isPaid) stringResource(id = R.string.paid) else stringResource(id = R.string.unpaid),
            color = Color.DarkGray,
            fontSize = MaterialTheme.typography.body1.fontSize,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )

        Divider(modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp))

        Text(
            text = stringResource(id = R.string.reminder_date_label),
            color = Color.DarkGray,
            fontSize = MaterialTheme.typography.caption.fontSize,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Text(
            text = DateUtil.getDateString(billInfo.notifDate),
            fontSize = MaterialTheme.typography.body1.fontSize,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Divider(modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp))

        Button(
            onClick = {
                billInputViewModel.save(billInfo.toBillDetail())
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 5.dp,
                pressedElevation = 10.dp,
                disabledElevation = 0.dp
            ),
            modifier = Modifier
                .padding(8.dp, 64.dp, 8.dp, 0.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.mark_as_paid))
        }

        OutlinedButton(
            onClick = {
                billInputViewModel.edit(billInfo)
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 5.dp,
                pressedElevation = 10.dp,
                disabledElevation = 0.dp
            ),
            modifier = Modifier
                .padding(8.dp, 8.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.edit))
        }
    }

}