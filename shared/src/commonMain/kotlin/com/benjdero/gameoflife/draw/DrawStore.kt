package com.benjdero.gameoflife.draw

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.draw.DrawStore.Intent
import com.benjdero.gameoflife.draw.DrawStore.State
import com.benjdero.gameoflife.model.World

internal interface DrawStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data class OnDraw(val x: Int, val y: Int) : Intent()
        data class OnDrawValue(val x: Int, val y: Int, val cell: Boolean) : Intent()
        data object ClearWorld : Intent()
        data object RandomWorld : Intent()
        data object ShowGrid : Intent()
        data object IncreaseLeft : Intent()
        data object DecreaseLeft : Intent()
        data object IncreaseTop : Intent()
        data object DecreaseTop : Intent()
        data object IncreaseRight : Intent()
        data object DecreaseRight : Intent()
        data object IncreaseBottom : Intent()
        data object DecreaseBottom : Intent()
    }

    data class State(
        val world: World,
        val showGrid: Boolean = false,
        val allowDecreaseWidth: Boolean = world.width > 1,
        val allowDecreaseHeight: Boolean = world.height > 1
    )
}