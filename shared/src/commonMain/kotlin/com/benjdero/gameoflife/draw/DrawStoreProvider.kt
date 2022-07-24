package com.benjdero.gameoflife.draw

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.benjdero.gameoflife.draw.DrawStore.Intent
import com.benjdero.gameoflife.draw.DrawStore.State

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
                Intent.IncreaseLeft -> increaseLeft(getState())
                Intent.DecreaseLeft -> decreaseLeft(getState())
                Intent.IncreaseTop -> increaseTop(getState())
                Intent.DecreaseTop -> decreaseTop(getState())
                Intent.IncreaseRight -> increaseRight(getState())
                Intent.DecreaseRight -> decreaseRight(getState())
                Intent.IncreaseBottom -> increaseBottom(getState())
                Intent.DecreaseBottom -> decreaseBottom(getState())
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

        fun increaseLeft(state: State) {
            state.world.apply {
                val newWidth: Int = width + 1
                val newWorld =
                    BooleanArray(newWidth * height) { index: Int ->
                        val i: Int = index
                        if (i % newWidth == 0)
                            false
                        else
                            cells[i - 1 - i / newWidth]
                    }
                dispatch(Msg.WidthChanged(newWidth, newWorld))
            }
        }

        fun decreaseLeft(state: State) {
            state.world.apply {
                if (width == 1)
                    return
                val newWidth: Int = width - 1
                val newWorld =
                    BooleanArray(newWidth * height) { index: Int ->
                        cells[index + 1 + index / newWidth]
                    }
                dispatch(Msg.WidthChanged(newWidth, newWorld))
            }
        }

        fun increaseTop(state: State) {
            state.world.apply {
                val newHeight: Int = height + 1
                val newWorld =
                    BooleanArray(width * newHeight) { index: Int ->
                        if (index / width == 0)
                            false
                        else
                            cells[index - width]
                    }
                dispatch(Msg.HeightChanged(newHeight, newWorld))
            }
        }

        fun decreaseTop(state: State) {
            state.world.apply {
                if (height == 1)
                    return
                val newHeight: Int = height - 1
                val newWorld =
                    BooleanArray(width * newHeight) { index: Int ->
                        cells[index + width]
                    }
                dispatch(Msg.HeightChanged(newHeight, newWorld))
            }
        }

        fun increaseRight(state: State) {
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

        fun decreaseRight(state: State) {
            state.world.apply {
                if (width == 1)
                    return
                val newWidth: Int = width - 1
                val newWorld =
                    BooleanArray(newWidth * height) { index: Int ->
                        cells[index + index / newWidth]
                    }
                dispatch(Msg.WidthChanged(newWidth, newWorld))
            }
        }

        fun increaseBottom(state: State) {
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

        fun decreaseBottom(state: State) {
            state.world.apply {
                if (height == 1)
                    return
                val newHeight: Int = height - 1
                val newWorld =
                    BooleanArray(width * newHeight) { index: Int ->
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
                is Msg.WidthChanged -> widthChanged(msg.width, msg.cells)
                is Msg.HeightChanged -> heightChanged(msg.height, msg.cells)
            }

        private fun State.widthChanged(width: Int, cells: BooleanArray) =
            copy(
                world = world.copy(width = width, cells = cells),
                allowDecreaseWidth = width > 1
            )

        private fun State.heightChanged(height: Int, cells: BooleanArray) =
            copy(
                world = world.copy(height = height, cells = cells),
                allowDecreaseHeight = height > 1
            )
    }
}