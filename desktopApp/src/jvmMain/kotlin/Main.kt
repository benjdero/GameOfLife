import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.benjdero.gameoflife.Root
import com.benjdero.gameoflife.RootComponent
import com.benjdero.gameoflife.model.dao.DaoService
import com.benjdero.gameoflife.model.dao.buildSqlDriver
import com.benjdero.gameoflife.ui.RootView

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    val component: Root = RootComponent(
        componentContext = DefaultComponentContext(
            lifecycle = lifecycle
        ),
        storeFactory = LoggingStoreFactory(TimeTravelStoreFactory()),
        daoService = DaoService(buildSqlDriver())
    )

    application {
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState
        ) {
            MaterialTheme {
                RootView(
                    component = component
                )
            }
        }
    }
}