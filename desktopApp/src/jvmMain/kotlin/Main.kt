import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.benjdero.gameoflife.Greeting

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            GreetingView()
        }
    }
}

@Composable
fun GreetingView() {
    Text(
        text = greet()
    )
}

fun greet(): String {
    return Greeting().greeting()
}