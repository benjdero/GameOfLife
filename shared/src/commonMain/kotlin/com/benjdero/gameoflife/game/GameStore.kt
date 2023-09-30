package com.benjdero.gameoflife.game

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.game.GameStore.Intent
import com.benjdero.gameoflife.game.GameStore.State

internal interface GameStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data object RunGame : Intent()
        data object PrevStep : Intent()
        data object NextStep : Intent()
        data object ShowGrid : Intent()
    }

    data class State(
        val running: Boolean = false,
        val generation: Int = 1,
        val world: World,
        val showGrid: Boolean = false,
        val history: List<BooleanArray> = emptyList()
    )
}