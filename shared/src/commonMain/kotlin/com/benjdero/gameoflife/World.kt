package com.benjdero.gameoflife

import kotlin.random.Random

data class World(
    val width: Int,
    val height: Int,
    val cells: Array<Boolean>
) {
    companion object {
        fun random(): World =
            random(15, 10)

        fun random(width: Int, height: Int): World =
            World(
                width = width,
                height = height,
                cells = Array(width * height) {
                    Random.nextBoolean()
                }
            )
    }

    fun get(x: Int, y: Int) =
        cells[x + y * width]

    internal fun mapIndexed(transform: (x: Int, y: Int, value: Boolean) -> Boolean): Array<Boolean> =
        Array(width * height) { index: Int ->
            transform(index % width, index / width, cells[index])
        }

    fun forEachIndexed(action: (x: Int, y: Int, value: Boolean) -> Unit) {
        for (index in cells.indices) {
            action(index % width, index / width, cells[index])
        }
    }
}