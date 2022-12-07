package id.fahrizal.billreminder.ui

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.fahrizal.billreminder.R
import id.fahrizal.billreminder.data.model.BillInfo
import id.fahrizal.billreminder.scheduler.util.DateUtil
import id.fahrizal.billreminder.ui.input.BillInputActivity
import id.fahrizal.billreminder.ui.theme.DarkGreen
import id.fahrizal.billreminder.ui.theme.LightGrey
import id.fahrizal.billreminder.ui.theme.Orange
import id.fahrizal.billreminder.util.CurrencyUtil
import java.util.*

@Composable
fun BillItem(billInfo: BillInfo = BillInfo()) {
    val context = LocalContext.current
    val fontWeight = if (billInfo.isPaid) FontWeight.Normal else FontWeight.SemiBold
    val colorItem = if (billInfo.isPaid) LightGrey else MaterialTheme.colors.surface

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = colorItem
    ) {
        Column(modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { showBillDetail(context, billInfo) })
        }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = billInfo.name,
                    modifier = Modifier.weight(3f),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = fontWeight
                )
                Text(
                    text = CurrencyUtil.getRupiahAmount(billInfo.amount),
                    modifier = Modifier.weight(2f),
                    textAlign = TextAlign.End,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = fontWeight
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = DateUtil.getDateString(billInfo.notifDate),
                    modifier = Modifier
                        .weight(3f)
                        .padding(vertical = 4.dp),
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    color = Color.DarkGray
                )
                Text(
                    text = getBillStatus(billInfo),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    textAlign = TextAlign.End,
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    color = getBillStatusFontColor(billInfo)
                )
            }
        }
    }
}

private fun showBillDetail(context: Context, billInfo: BillInfo) {
    val intent = BillInputActivity.createIntent(context, billInfo.billId)
    context.startActivity(intent)
}

@Composable
private fun getBillStatus(billInfo: BillInfo, currentTime: Long = Date().time): String {
    return if (billInfo.isPaid) {
        stringResource(R.string.paid)
    } else if (currentTime > DateUtil.plusDay(billInfo.notifDate, 1)) {
        stringResource(R.string.overdue)
    } else if (currentTime > billInfo.notifDate) {
        stringResource(R.string.due)
    } else {
        stringResource(R.string.unpaid)
    }
}

private fun getBillStatusFontColor(billInfo: BillInfo, currentTime: Long = Date().time): Color {
    return if (billInfo.isPaid) {
        DarkGreen
    } else if (currentTime > DateUtil.plusDay(billInfo.notifDate, 1)) {
        Color.Red
    } else if (currentTime > billInfo.notifDate) {
        Orange
    } else {
        Color.Unspecified
    }
}