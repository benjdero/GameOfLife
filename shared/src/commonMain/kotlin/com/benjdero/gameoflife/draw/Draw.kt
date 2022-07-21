package com.benjdero.gameoflife.draw

import com.arkivanov.decompose.value.Value

interface Draw {
    val models: Value<Model>

    fun onDraw(x: Int, y: Int)

    fun onDrawValue(x: Int, y: Int, value: Boolean)

    fun decreaseWidth()

    fun increaseWidth()

    fun decreaseHeight()

    fun increaseHeight()

    data class Model(
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