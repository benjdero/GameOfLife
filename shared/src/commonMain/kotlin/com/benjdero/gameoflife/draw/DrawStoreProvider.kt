package com.benjdero.gameoflife.draw

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.benjdero.gameoflife.draw.DrawStore.Intent
import com.benjdero.gameoflife.draw.DrawStore.State
import kotlin.math.max

internal class DrawStoreProvider(
    private val storeFactory: StoreFactory
) {
    fun provide(): DrawStore =
        object : DrawStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "DrawStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class WorldUpdate(val world: Array<Array<Boolean>>) : Msg()
        data class WidthChanged(val width: Int, val world: Array<Array<Boolean>>) : Msg()
        data class HeightChanged(val height: Int, val world: Array<Array<Boolean>>) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnDraw -> onDraw(intent.x, intent.y, getState())
                is Intent.OnDrawValue -> onDrawValue(intent.x, intent.y, intent.value, getState())
                Intent.DecreaseWidth -> decreaseWidth(getState())
                Intent.IncreaseWidth -> increaseWidth(getState())
                Intent.DecreaseHeight -> decreaseHeight(getState())
                Intent.IncreaseHeight -> increaseHeight(getState())
            }
        }

        private fun onDraw(x: Int, y: Int, state: State) {
            val world: Array<Array<Boolean>> = state.world
            val newWorld: Array<Array<Boolean>> =
                Array(state.height) { r ->
                    Array(state.width) { c ->
                        if (x == c && r == y)
                            !world[r][c]
                        else
                            world[r][c]
                    }
                }
            dispatch(Msg.WorldUpdate(newWorld))
        }

        private fun onDrawValue(x: Int, y: Int, value: Boolean, state: State) {
            val world: Array<Array<Boolean>> = state.world
            val newWorld: Array<Array<Boolean>> =
                Array(state.height) { r ->
                    Array(state.width) { c ->
                        if (x == c && r == y)
                            value
                        else
                            world[r][c]
                    }
                }
            dispatch(Msg.WorldUpdate(newWorld))
        }

        fun decreaseWidth(state: State) {
            val newWidth: Int = max(state.width - 1, 1)
            val newWorld: Array<Array<Boolean>> =
                Array(state.height) { r ->
                    Array(newWidth) { c ->
                        state.world[r][c]
                    }
                }
            dispatch(Msg.WidthChanged(newWidth, newWorld))
        }

        fun increaseWidth(state: State) {
            val newWidth: Int = state.width + 1
            val newWorld: Array<Array<Boolean>> =
                Array(state.height) { r ->
                    Array(newWidth) { c ->
                        if (c == state.width)
                            false
                        else
                            state.world[r][c]
                    }
                }
            dispatch(Msg.WidthChanged(newWidth, newWorld))
        }

        fun decreaseHeight(state: State) {
            val newHeight: Int = max(state.height - 1, 1)
            val newWorld: Array<Array<Boolean>> =
                Array(newHeight) { r ->
                    Array(state.width) { c ->
                        state.world[r][c]
                    }
                }
            dispatch(Msg.HeightChanged(newHeight, newWorld))
        }

        fun increaseHeight(state: State) {
            val newHeight: Int = state.height + 1
            val newWorld: Array<Array<Boolean>> =
                Array(newHeight) { r ->
                    Array(state.width) { c ->
                        if (r == state.height)
                            false
                        else
                            state.world[r][c]
                    }
                }
            dispatch(Msg.HeightChanged(newHeight, newWorld))
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.WorldUpdate -> copy(world = msg.world)
                is Msg.WidthChanged -> copy(width = msg.width, world = msg.world)
                is Msg.HeightChanged -> copy(height = msg.height, world = msg.world)
            }
    }
}