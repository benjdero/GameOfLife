package com.benjdero.gameoflife.draw

import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.World

interface Draw {
    val models: Value<Model>

    fun onDraw(x: Int, y: Int)

    fun onDrawValue(x: Int, y: Int, value: Boolean)

    fun decreaseWidth()

    fun increaseWidth()

    fun decreaseHeight()

    fun increaseHeight()

    fun finish()

    data class Model(
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

    sealed class Output {
        data class Finish(val world: World) : Output()
    }
}