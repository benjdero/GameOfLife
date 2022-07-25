package com.benjdero.gameoflife.draw

import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.World

interface Draw {
    val models: Value<Model>

    fun onDraw(x: Int, y: Int)

    fun onDrawValue(x: Int, y: Int, cell: Boolean)

    fun toggleGrid()

    fun increaseLeft()

    fun decreaseLeft()

    fun increaseTop()

    fun decreaseTop()

    fun increaseRight()

    fun decreaseRight()

    fun increaseBottom()

    fun decreaseBottom()

    fun finish()

    data class Model(
        val world: World,
        val showGrid: Boolean,
        val allowDecreaseWidth: Boolean,
        val allowDecreaseHeight: Boolean,
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
        data class Finish(val world: World) : Output()
    }
}