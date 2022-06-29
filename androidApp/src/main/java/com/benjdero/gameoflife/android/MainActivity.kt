package com.benjdero.gameoflife.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.WorldComponent
import com.benjdero.gameoflife.ui.RootView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component: World = WorldComponent(
            componentContext = defaultComponentContext(),
            storeFactory = LoggingStoreFactory(TimeTravelStoreFactory())
        )

        setContent {
            RootView(component)
        }
    }
}
