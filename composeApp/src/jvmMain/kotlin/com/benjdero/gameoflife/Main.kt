package com.benjdero.gameoflife

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.benjdero.gameoflife.model.dao.DaoService
import com.benjdero.gameoflife.model.dao.SqlDriverFactory

fun main() {
    val lifecycle = LifecycleRegistry()
    val component: RootComponent = RootComponentImpl(
        componentContext = DefaultComponentContext(
            lifecycle = lifecycle
        ),
        storeFactory = LoggingStoreFactory(TimeTravelStoreFactory()),
        daoService = DaoService(
            sqlDriverFactory = SqlDriverFactory()
        )
    )

    application {
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Game of Life",
            icon = painterResource("drawable/icon.svg")
        ) {
            RootView(
                component = component
            )
        }
    }
}