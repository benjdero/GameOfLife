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
        data class WorldUpdate(val cells: BooleanArray) : Msg()
        data class WidthChanged(val width: Int, val cells: BooleanArray) : Msg()
        data class HeightChanged(val height: Int, val cells: BooleanArray) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnDraw -> onDraw(intent.x, intent.y, getState())
                is Intent.OnDrawValue -> onDrawValue(intent.x, intent.y, intent.cell, getState())
                Intent.DecreaseWidth -> decreaseWidth(getState())
                Intent.IncreaseWidth -> increaseWidth(getState())
                Intent.DecreaseHeight -> decreaseHeight(getState())
                Intent.IncreaseHeight -> increaseHeight(getState())
            }
        }

        private fun onDraw(xD: Int, yD: Int, state: State) {
            dispatch(
                Msg.WorldUpdate(
                    state.world.mapIndexed { x: Int, y: Int, cell: Boolean ->
                        if (x == xD && y == yD)
                            !cell
                        else
                            cell
                    }
                )
            )
        }

        private fun onDrawValue(xD: Int, yD: Int, cellD: Boolean, state: State) {
            dispatch(
                Msg.WorldUpdate(
                    state.world.mapIndexed { x: Int, y: Int, cell: Boolean ->
                        if (x == xD && y == yD)
                            cellD
                        else
                            cell
                    }
                )
            )
        }

        fun decreaseWidth(state: State) {
            state.world.apply {
                val newWidth: Int = max(width - 1, 1)
                val newWorld =
                    BooleanArray(newWidth * height) { index: Int ->
                        cells[index + index / newWidth]
                    }
                dispatch(Msg.WidthChanged(newWidth, newWorld))
            }
        }

        fun increaseWidth(state: State) {
            state.world.apply {
                val newWidth: Int = width + 1
                val newWorld =
                    BooleanArray(newWidth * height) { index: Int ->
                        val i: Int = index
                        if (i % newWidth == width)
                            false
                        else
                            cells[i - i / newWidth]
                    }
                dispatch(Msg.WidthChanged(newWidth, newWorld))
            }
        }

        fun decreaseHeight(state: State) {
            state.world.apply {
                val newHeight: Int = max(height - 1, 1)
                val newWorld =
                    BooleanArray(width * newHeight) { index: Int ->
                        cells[index]
                    }
                dispatch(Msg.HeightChanged(newHeight, newWorld))
            }
        }

        fun increaseHeight(state: State) {
            state.world.apply {
                val newHeight: Int = height + 1
                val newWorld =
                    BooleanArray(width * newHeight) { index: Int ->
                        if (index / width == height)
                            false
                        else
                            cells[index]
                    }
                dispatch(Msg.HeightChanged(newHeight, newWorld))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.WorldUpdate -> copy(world = world.copy(cells = msg.cells))
                is Msg.WidthChanged -> copy(world = world.copy(width = msg.width, cells = msg.cells))
                is Msg.HeightChanged -> copy(world = world.copy(height = msg.height, cells = msg.cells))
            }
    }
}