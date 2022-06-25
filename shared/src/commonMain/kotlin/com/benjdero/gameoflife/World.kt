package com.benjdero.gameoflife

import com.arkivanov.decompose.value.Value

interface World {
    val models: Value<Model>

    fun nextGeneration()

    data class Model(
        val width: Int,
        val height: Int,
        val world: Array<Array<Boolean>>
    )
}