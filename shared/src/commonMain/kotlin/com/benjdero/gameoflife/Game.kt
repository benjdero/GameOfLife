package com.benjdero.gameoflife

import com.arkivanov.decompose.value.Value

interface Game {
    val models: Value<Model>

    fun runGame()

    fun nextStep()

    data class Model(
        val running: Boolean,
        val width: Int,
        val height: Int,
        val world: Array<Array<Boolean>>
    ) {
        val flatWorld: List<FlatWorldElement> = world.flatten().mapIndexed { index: Int, value: Boolean ->
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