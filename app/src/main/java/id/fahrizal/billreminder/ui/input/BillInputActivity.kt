package id.fahrizal.billreminder.ui.input

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import id.fahrizal.billreminder.ui.theme.BillReminderTheme

class BillInputActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillInputScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    BillReminderTheme {
        BillInputScreen()
    }
}