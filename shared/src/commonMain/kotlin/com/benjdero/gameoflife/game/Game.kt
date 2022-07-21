package com.benjdero.gameoflife.game

import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.World

interface Game {
    val models: Value<Model>

    fun runGame()

    fun nextStep()

    data class Model(
        val running: Boolean,
        val world: World
    ) {
        val flatWorld: List<FlatWorldElement> = world.cells.mapIndexed { index: Int, value: Boolean ->
            FlatWorldElement(
                id = index,
                cell = value
            )
        }
    }

    data class FlatWorldElement(
        val id: Int,
        val cell: Boolean
    )
}