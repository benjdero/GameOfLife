package com.benjdero.gameoflife

import com.arkivanov.mvikotlin.core.store.Store
import com.benjdero.gameoflife.WorldStore.Intent
import com.benjdero.gameoflife.WorldStore.State
import kotlin.random.Random

internal interface WorldStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        object RunGame : Intent()
        object NextStep : Intent()
    }

    data class State(
        val running: Boolean = false,
        val width: Int = 15,
        val height: Int = 10,
        val world: Array<Array<Boolean>> = Array(height) {
            Array(width) {
                Random.nextBoolean()
            }
        }
    )
}