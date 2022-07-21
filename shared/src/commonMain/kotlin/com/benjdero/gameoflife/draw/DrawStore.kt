package com.benjdero.gameoflife.draw

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.draw.DrawStore.Intent
import com.benjdero.gameoflife.draw.DrawStore.State
import kotlin.random.Random

internal interface DrawStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data class OnDraw(val x: Int, val y: Int) : Intent()
        data class OnDrawValue(val x: Int, val y: Int, val value: Boolean) : Intent()
        object DecreaseWidth : Intent()
        object IncreaseWidth : Intent()
        object DecreaseHeight : Intent()
        object IncreaseHeight : Intent()
    }

    data class State(
        val width: Int = 15,
        val height: Int = 10,
        val world: Array<Boolean> = Array(width * height) {
            Random.nextBoolean()
        }
    ) {
        internal fun arrayMapIndexed(transform: (x: Int, y: Int, value: Boolean) -> Boolean): Array<Boolean> =
            Array(world.size) { index: Int ->
                transform(index % width, index / width, world[index])
            }
    }
}