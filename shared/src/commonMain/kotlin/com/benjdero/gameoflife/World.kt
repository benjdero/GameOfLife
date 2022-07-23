package com.benjdero.gameoflife

import kotlin.random.Random

data class World(
    val width: Int,
    val height: Int,
    val cells: BooleanArray
) {
    companion object {
        fun random(): World =
            random(15, 10)

        fun random(width: Int, height: Int): World =
            World(
                width = width,
                height = height,
                cells = BooleanArray(width * height) {
                    Random.nextBoolean()
                }
            )
    }

    fun isWithinBounds(x: Int, y: Int): Boolean =
        x in 0 until width &&
                y in 0 until height

    fun get(x: Int, y: Int) =
        cells[x + y * width]

    internal fun mapIndexed(transform: (x: Int, y: Int, cell: Boolean) -> Boolean): BooleanArray =
        BooleanArray(width * height) { index: Int ->
            transform(index % width, index / width, cells[index])
        }

    fun forEachIndexed(action: (x: Int, y: Int, cell: Boolean) -> Unit) {
        for (index in cells.indices) {
            action(index % width, index / width, cells[index])
        }
    }
}