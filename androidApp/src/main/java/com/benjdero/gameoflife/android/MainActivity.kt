package com.benjdero.gameoflife.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.benjdero.gameoflife.game.Game
import com.benjdero.gameoflife.game.GameComponent
import com.benjdero.gameoflife.ui.game.GameView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component: Game = GameComponent(
            componentContext = defaultComponentContext(),
            storeFactory = LoggingStoreFactory(TimeTravelStoreFactory())
        )

        setContent {
            GameView(component)
        }
    }
}
