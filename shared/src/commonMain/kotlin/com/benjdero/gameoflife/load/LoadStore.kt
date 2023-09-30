package com.benjdero.gameoflife.load

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.load.LoadStore.Action
import com.benjdero.gameoflife.load.LoadStore.Intent
import com.benjdero.gameoflife.load.LoadStore.State

internal interface LoadStore : Store<Intent, State, Action> {
    sealed class Intent {
        data class DeleteWorld(val id: Long) : Intent()
    }

    data class State(
        val worldList: List<World> = emptyList()
    )

    sealed class Action {
        data object Initialize : Action()
    }
}