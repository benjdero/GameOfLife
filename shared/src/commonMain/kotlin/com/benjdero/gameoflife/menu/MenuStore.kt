package com.benjdero.gameoflife.menu

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.menu.MenuStore.Intent
import com.benjdero.gameoflife.menu.MenuStore.State

internal interface MenuStore : Store<Intent, State, Nothing> {
    sealed class Intent

    data class State(
        val unused: Int = 0
    )
}