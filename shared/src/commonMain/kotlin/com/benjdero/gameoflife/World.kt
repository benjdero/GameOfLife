package com.benjdero.gameoflife

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlin.random.Random

@Parcelize
data class World(
    val saved: Saved,
    val width: Int,
    val height: Int,
    val cells: BooleanArray
) : Parcelable {
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

    @Parcelize
    sealed class Saved : Parcelable {
        data object Not : Saved()
        data class AsWorld(val id: Long) : Saved()
    }
}