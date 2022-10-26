package id.fahrizal.billreminder.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import id.fahrizal.billreminder.scheduler.SchedulerManager
import id.fahrizal.billreminder.scheduler.util.DateUtil
import id.fahrizal.billreminder.ui.theme.BillReminderTheme
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillScreen(mainViewModel)
        }
        fetchBills()
    }

    private fun fetchBills() {
        mainViewModel.fetchBills()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BillReminderTheme {
        BillScreen()
    }
}