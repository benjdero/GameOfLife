package com.benjdero.gameoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.benjdero.gameoflife.model.dao.DaoService
import com.benjdero.gameoflife.model.dao.SqlDriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component: RootComponent = RootComponentImpl(
            componentContext = defaultComponentContext(),
            storeFactory = LoggingStoreFactory(TimeTravelStoreFactory()),
            daoService = DaoService(
                sqlDriverFactory = SqlDriverFactory(
                    context = this
                )
            )
        )

        setContent {
            RootView(
                component = component
            )
        }
    }
}
