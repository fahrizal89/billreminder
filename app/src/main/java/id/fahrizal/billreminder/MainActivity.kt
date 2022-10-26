package id.fahrizal.billreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.fahrizal.billreminder.scheduler.SchedulerManager
import id.fahrizal.billreminder.scheduler.util.DateUtil
import id.fahrizal.billreminder.ui.theme.BillReminderTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillReminderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
        val time = DateUtil.getDate(8,46,0).time
        SchedulerManager.set(this,time, "test", "body")
        Timber.d("SCHEDULE ACTIVATED!")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello... $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BillReminderTheme {
        Greeting("Android")
    }
}