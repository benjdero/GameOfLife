package com.benjdero.gameoflife.draw

import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.World

interface Draw {
    val models: Value<Model>

    fun onDraw(x: Int, y: Int)

    fun onDrawValue(x: Int, y: Int, cell: Boolean)

    fun clearWorld()

    fun randomWorld()

    fun toggleGrid()

    fun increaseLeft()

    fun decreaseLeft()

    fun increaseTop()

    fun decreaseTop()

    fun increaseRight()

    fun decreaseRight()

    fun increaseBottom()

    fun decreaseBottom()

    fun load()

    fun save(world: World)

    fun finish()

    fun goBack()

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
        data object GoBack : Output()
        data object Load : Output()
        data class Save(val world: World) : Output()
    }
}