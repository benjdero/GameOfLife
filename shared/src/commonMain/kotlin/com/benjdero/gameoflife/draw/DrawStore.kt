package com.benjdero.gameoflife.draw

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.draw.DrawStore.Intent
import com.benjdero.gameoflife.draw.DrawStore.State
import kotlin.random.Random

internal interface DrawStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data class OnDraw(val x: Int, val y: Int) : Intent()
        data class OnDrawValue(val x: Int, val y: Int, val value: Boolean) : Intent()
    }

    data class State(
        val width: Int = 15,
        val height: Int = 10,
        val world: Array<Array<Boolean>> = Array(height) {
            Array(width) {
                Random.nextBoolean()
            }
        }
    )
}