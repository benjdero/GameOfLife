package com.benjdero.gameoflife.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.benjdero.gameoflife.Root
import com.benjdero.gameoflife.RootComponent
import com.benjdero.gameoflife.model.dao.DaoService
import com.benjdero.gameoflife.model.dao.buildSqlDriver
import com.benjdero.gameoflife.ui.RootView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component: Root = RootComponent(
            componentContext = defaultComponentContext(),
            storeFactory = LoggingStoreFactory(TimeTravelStoreFactory()),
            daoService = DaoService(buildSqlDriver(this))
        )

        setContent {
            RootView(component)
        }
    }
}
