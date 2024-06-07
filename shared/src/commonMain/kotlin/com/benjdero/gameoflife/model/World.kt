package com.benjdero.gameoflife.model

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class World(
    val saved: Saved,
    val width: Int,
    val height: Int,
    val cells: BooleanArray
) {
    companion object {
        fun random(): World =
            random(15, 10)

        fun random(width: Int, height: Int): World =
            World(
                saved = Saved.Not,
                width = width,
                height = height,
                cells = BooleanArray(width * height) { index: Int ->
                    if (index / width == 0 || index / width == height - 1 || index % width == 0 || index % width == width - 1)
                        false
                    else
                        Random.nextBoolean()
                }
            )
    }

    fun isWithinBounds(x: Int, y: Int): Boolean =
        x in 0 until width && y in 0 until height

    fun isAlive(x: Int, y: Int): Boolean =
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

    /**
     * Allow to draw world on iOS < 15.0
     */
    fun toFlatWorld(): List<FlatWorldElement> =
        cells.mapIndexed { index: Int, cell: Boolean ->
            FlatWorldElement(
                id = index,
                cell = cell
            )
        }

    @Serializable
    sealed class Saved {
        data object Not : Saved()
        data class AsWorld(val id: Long, val name: String) : Saved()
    }
}