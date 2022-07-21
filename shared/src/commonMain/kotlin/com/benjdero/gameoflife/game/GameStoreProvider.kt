package com.benjdero.gameoflife.game

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.game.GameStore.Intent
import com.benjdero.gameoflife.game.GameStore.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class GameStoreProvider(
    private val storeFactory: StoreFactory,
    val world: World
) {
    fun provide(): GameStore =
        object : GameStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "WorldStore",
            initialState = State(
                world = world
            ),
            bootstrapper = SimpleBootstrapper(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class RunGame(val running: Boolean) : Msg()
        data class WorldUpdate(val cells: Array<Boolean>) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.RunGame -> runGame(getState)
                Intent.NextStep -> nextStep(getState())
            }
        }

        private fun runGame(getState: () -> State) {
            val isRunning = getState().running
            dispatch(Msg.RunGame(!isRunning))
            if (!isRunning) {
                scope.launch {
                    while (true) {
                        val state: State = getState()
                        if (!state.running)
                            return@launch
                        nextStep(state)
                        delay(500)
                    }
                }
            }
        }

        private fun nextStep(state: State) {
            val nextWorld: Array<Boolean> = calcNextWorld(state)
            dispatch(Msg.WorldUpdate(nextWorld))
        }

        private fun calcNextWorld(state: State): Array<Boolean> =
            state.world.mapIndexed { x: Int, y: Int, cell: Boolean ->
                val neighborsCount: Int = countNeighbors(x, y, state.world)
                if (cell)
                    neighborsCount in 2..3
                else
                    neighborsCount == 3
            }

        private fun countNeighbors(x: Int, y: Int, world: World): Int {
            var count = 0
            for (yD in y - 1..y + 1) {
                for (yX in x - 1..x + 1) {
                    if (
                        world.isWithinBounds(yX, yD) &&
                        (yD != y || yX != x) &&
                        world.get(yX, yD)
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
                is Msg.RunGame -> copy(running = msg.running)
                is Msg.WorldUpdate -> copy(world = world.copy(cells = msg.cells))
            }
    }
}