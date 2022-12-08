package id.fahrizal.billreminder.ui.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import id.fahrizal.billreminder.ui.theme.BillReminderTheme

@AndroidEntryPoint
class BillInputActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillInputScreen()
        }
    }

    companion object {

        const val BILL_ID = "BILL_ID"

        fun createIntent(context: Context, billId: Long): Intent {
            val intent = Intent(context, BillInputActivity::class.java)
            intent.putExtra(BILL_ID, billId)
            return intent
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    BillReminderTheme {
        InputForm()
    }
}