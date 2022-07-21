package com.benjdero.gameoflife.draw

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.draw.DrawStore.Intent
import com.benjdero.gameoflife.draw.DrawStore.State

internal interface DrawStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data class OnDraw(val x: Int, val y: Int) : Intent()
        data class OnDrawValue(val x: Int, val y: Int, val cell: Boolean) : Intent()
        object DecreaseWidth : Intent()
        object IncreaseWidth : Intent()
        object DecreaseHeight : Intent()
        object IncreaseHeight : Intent()
    }

    data class State(
        val world: World = World.random()
    )
}