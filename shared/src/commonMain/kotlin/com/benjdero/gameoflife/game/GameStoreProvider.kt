package com.benjdero.gameoflife.game

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.benjdero.gameoflife.Speed
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.game.GameStore.Intent
import com.benjdero.gameoflife.game.GameStore.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class GameStoreProvider(
    private val storeFactory: StoreFactory,
    private val world: World?
) {
    fun provide(): GameStore =
        object : GameStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "WorldStore",
            initialState = State(
                world = world ?: World.random()
            ),
            bootstrapper = SimpleBootstrapper(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class RunGame(val running: Boolean) : Msg()
        data class WorldUpdate(val cells: BooleanArray, val width: Int, val height: Int) : Msg()
        data object WorldRollback : Msg()
        data object ToggleShowGrid : Msg()
        data class SetSpeed(val speed: Speed, val canSpeedUp: Boolean, val canSpeedDown: Boolean) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.RunGame -> runGame(getState)
                Intent.PrevStep -> prevStep()
                Intent.NextStep -> nextStep(getState())
                Intent.ShowGrid -> showGrid()
                Intent.SpeedUp -> speedUp(getState())
                Intent.SpeedDown -> speedDown(getState())
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
                        val delay: Long =
                            when (state.speed) {
                                Speed.NORMAL -> 1000
                                Speed.FAST_2X -> 500
                                Speed.FAST_4X -> 250
                                Speed.FAST_10X -> 100
                            }
                        delay(delay)
                    }
                }
            }
        }

        private fun prevStep() {
            dispatch(Msg.WorldRollback)
        }

        private fun nextStep(state: State) {
            var nextWorld: BooleanArray =
                state.world.mapIndexed { x: Int, y: Int, cell: Boolean ->
                    val neighborsCount: Int = countNeighbors(x, y, state.world)
                    if (cell)
                        neighborsCount in 2..3
                    else
                        neighborsCount == 3
                }

            var width: Int = state.world.width
            var height: Int = state.world.height
            if (needIncreaseLeft(nextWorld, width)) {
                nextWorld = increaseLeft(nextWorld, width, height)
                width += 1
            }
            if (needIncreaseTop(nextWorld, width)) {
                nextWorld = increaseTop(nextWorld, width, height)
                height += 1
            }
            if (needIncreaseRight(nextWorld, width)) {
                nextWorld = increaseRight(nextWorld, width, height)
                width += 1
            }
            if (needIncreaseBottom(nextWorld, width, height)) {
                nextWorld = increaseBottom(nextWorld, width, height)
                height += 1
            }
            dispatch(Msg.WorldUpdate(nextWorld, width, height))
        }

        private fun needIncreaseLeft(world: BooleanArray, width: Int): Boolean {
            world.forEachIndexed { index: Int, cell: Boolean ->
                if (index % width == 0 && cell)
                    return true
            }

            return false
        }

        private fun needIncreaseTop(world: BooleanArray, width: Int): Boolean {
            world.forEachIndexed { index: Int, cell: Boolean ->
                if (index / width == 0 && cell)
                    return true
            }

            return false
        }

        private fun needIncreaseRight(world: BooleanArray, width: Int): Boolean {
            world.forEachIndexed { index: Int, cell: Boolean ->
                if (index % width == width - 1 && cell)
                    return true
            }

            return false
        }

        private fun needIncreaseBottom(world: BooleanArray, width: Int, height: Int): Boolean {
            world.forEachIndexed { index: Int, cell: Boolean ->
                if (index / width == height - 1 && cell)
                    return true
            }

            return false
        }

        private fun increaseLeft(world: BooleanArray, width: Int, height: Int): BooleanArray {
            val newWidth: Int = width + 1
            val newWorld =
                BooleanArray(newWidth * height) { index: Int ->
                    val i: Int = index
                    if (i % newWidth == 0)
                        false
                    else
                        world[i - 1 - i / newWidth]
                }

            return newWorld
        }

        private fun increaseTop(world: BooleanArray, width: Int, height: Int): BooleanArray {
            val newHeight: Int = height + 1
            val newWorld =
                BooleanArray(width * newHeight) { index: Int ->
                    if (index / width == 0)
                        false
                    else
                        world[index - width]
                }

            return newWorld
        }

        private fun increaseRight(world: BooleanArray, width: Int, height: Int): BooleanArray {
            val newWidth: Int = width + 1
            val newWorld =
                BooleanArray(newWidth * height) { index: Int ->
                    val i: Int = index
                    if (i % newWidth == width)
                        false
                    else
                        world[i - i / newWidth]
                }

            return newWorld
        }

        private fun increaseBottom(world: BooleanArray, width: Int, height: Int): BooleanArray {
            val newHeight: Int = height + 1
            val newWorld =
                BooleanArray(width * newHeight) { index: Int ->
                    if (index / width == height)
                        false
                    else
                        world[index]
                }

            return newWorld
        }

        private fun countNeighbors(x: Int, y: Int, world: World): Int {
            var count = 0
            for (yD in y - 1..y + 1) {
                for (yX in x - 1..x + 1) {
                    if (
                        world.isWithinBounds(yX, yD) &&
                        (yD != y || yX != x) &&
                        world.isAlive(yX, yD)
                    ) {
                        count++
                    }
                }
            }
            return count
        }

        private fun showGrid() {
            dispatch(Msg.ToggleShowGrid)
        }

        private fun speedUp(state: State) {
            val newSpeed: Speed =
                when (state.speed) {
                    Speed.NORMAL -> Speed.FAST_2X
                    Speed.FAST_2X -> Speed.FAST_4X
                    Speed.FAST_4X -> Speed.FAST_10X
                    Speed.FAST_10X -> Speed.FAST_10X
                }
            dispatch(
                Msg.SetSpeed(
                    speed = newSpeed,
                    canSpeedUp = newSpeed != Speed.FAST_10X,
                    canSpeedDown = true
                )
            )
        }

        private fun speedDown(state: State) {
            val newSpeed: Speed =
                when (state.speed) {
                    Speed.NORMAL -> Speed.NORMAL
                    Speed.FAST_2X -> Speed.NORMAL
                    Speed.FAST_4X -> Speed.FAST_2X
                    Speed.FAST_10X -> Speed.FAST_4X
                }
            dispatch(
                Msg.SetSpeed(
                    speed = newSpeed,
                    canSpeedUp = true,
                    canSpeedDown = newSpeed != Speed.NORMAL
                )
            )
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.RunGame -> copy(running = msg.running)
                is Msg.WorldUpdate -> worldUpdate(cells = msg.cells, width = msg.width, height = msg.height)
                is Msg.WorldRollback -> worldRollback()
                Msg.ToggleShowGrid -> copy(showGrid = !showGrid)
                is Msg.SetSpeed -> copy(speed = msg.speed, canSpeedUp = msg.canSpeedUp, canSpeedDown = msg.canSpeedDown)
            }

        private fun State.worldUpdate(cells: BooleanArray, width: Int, height: Int) =
            copy(
                generation = generation + 1,
                world = world.copy(
                    cells = cells,
                    width = width,
                    height = height
                ),
                history = history.plus(world).let {
                    if (it.size > 10)
                        it.drop(1)
                    else
                        it
                }
            )

        private fun State.worldRollback(): State =
            copy(
                generation = generation - 1,
                world = history.last(),
                history = history.dropLast(1)
            )
    }
}
