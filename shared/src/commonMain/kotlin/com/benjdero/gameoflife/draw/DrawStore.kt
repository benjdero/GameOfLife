package com.benjdero.gameoflife.draw

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.draw.DrawStore.Intent
import com.benjdero.gameoflife.draw.DrawStore.State

internal interface DrawStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data class OnDraw(val x: Int, val y: Int) : Intent()
        data class OnDrawValue(val x: Int, val y: Int, val cell: Boolean) : Intent()
        object ClearWorld : Intent()
        object RandomWorld : Intent()
        object ShowGrid : Intent()
        object IncreaseLeft : Intent()
        object DecreaseLeft : Intent()
        object IncreaseTop : Intent()
        object DecreaseTop : Intent()
        object IncreaseRight : Intent()
        object DecreaseRight : Intent()
        object IncreaseBottom : Intent()
        object DecreaseBottom : Intent()
    }

    data class State(
        val world: World = World.random(),
        val showGrid: Boolean = false,
        val allowDecreaseWidth: Boolean = world.width > 1,
        val allowDecreaseHeight: Boolean = world.height > 1
    )
}