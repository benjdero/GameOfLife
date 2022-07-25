package com.benjdero.gameoflife.game

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.game.GameStore.Intent
import com.benjdero.gameoflife.game.GameStore.State

internal interface GameStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        object RunGame : Intent()
        object PrevStep : Intent()
        object NextStep : Intent()
        object ShowGrid : Intent()
    }

    data class State(
        val running: Boolean = false,
        val generation: Int = 1,
        val world: World,
        val showGrid: Boolean = false,
        val history: List<BooleanArray> = emptyList()
    )
}