package com.benjdero.gameoflife

import com.arkivanov.decompose.value.Value

interface World {
    val models: Value<Model>

    fun runGame()

    fun nextStep()

    data class Model(
        val running: Boolean,
        val width: Int,
        val height: Int,
        val world: Array<Array<Boolean>>
    )
}