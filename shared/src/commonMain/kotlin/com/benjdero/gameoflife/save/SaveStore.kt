package com.benjdero.gameoflife.save

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.save.SaveStore.Action
import com.benjdero.gameoflife.save.SaveStore.Intent
import com.benjdero.gameoflife.save.SaveStore.State

internal interface SaveStore : Store<Intent, State, Action> {
    sealed class Intent {
        data class SetName(val name: String) : Intent()
        data object ClearName : Intent()
        data object Save : Intent()
    }

    data class State(
        val name: String = "",
        val world: World,
        val canSave: Boolean = false,
        val saved: Boolean = false
    )

    sealed class Action {
        data object Initialize : Action()
    }
}