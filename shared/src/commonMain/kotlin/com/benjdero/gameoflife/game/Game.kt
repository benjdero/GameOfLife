package com.benjdero.gameoflife.game

import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.World

interface Game {
    val models: Value<Model>

    fun runGame()

    fun prevStep()

    fun nextStep()

    fun toggleGrid()

    fun goBack()

    data class Model(
        val running: Boolean,
        val generation: Int,
        val world: World,
        val showGrid: Boolean,
        val history: List<BooleanArray>
    ) {
        val flatWorld: List<FlatWorldElement> = world.cells.mapIndexed { index: Int, cell: Boolean ->
            FlatWorldElement(
                id = index,
                cell = cell
            )
        }
    }

    data class FlatWorldElement(
        val id: Int,
        val cell: Boolean
    )

    sealed class Output {
        object GoBack : Output()
    }
}