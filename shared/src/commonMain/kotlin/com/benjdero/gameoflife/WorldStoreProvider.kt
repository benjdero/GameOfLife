package com.benjdero.gameoflife

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.benjdero.gameoflife.WorldStore.Intent
import com.benjdero.gameoflife.WorldStore.State

internal class WorldStoreProvider(
    private val storeFactory: StoreFactory
) {
    fun provide(): WorldStore =
        object : WorldStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "WorldStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class WorldUpdate(val world: Array<Array<Boolean>>) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.NextGeneration -> nextGeneration(getState())
            }
        }

        private fun nextGeneration(state: State) {
            val nextWorld: Array<Array<Boolean>> = Array(state.height) { r ->
                Array(state.width) { c ->
                    val neighborsCount: Int = countNeighbors(r, c, state.height, state.width, state.world)
                    if (state.world[r][c])
                        neighborsCount in 2..3
                    else
                        neighborsCount == 3
                }
            }
            dispatch(Msg.WorldUpdate(nextWorld))
        }

        private fun countNeighbors(row: Int, col: Int, height: Int, width: Int, world: Array<Array<Boolean>>): Int {
            var count = 0
            for (r in row - 1..row + 1) {
                for (c in col - 1..col + 1) {
                    if (
                        r in 0 until height &&
                        c in 0 until width &&
                        (r != row || c != col) &&
                        world[r][c]
                    ) {
                        count++
                    }
                }
            }
            return count
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.WorldUpdate -> copy(world = msg.world)
            }
    }
}