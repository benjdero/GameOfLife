package com.benjdero.gameoflife.game

import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.Speed
import com.benjdero.gameoflife.World

interface Game {
    val models: Value<Model>

    fun runGame()

    fun prevStep()

    fun nextStep()

    fun toggleGrid()

    fun goBack()

    fun speedUp()

    fun speedDown()

    data class Model(
        val running: Boolean,
        val generation: Int,
        val speed: Speed,
        val canSpeedUp: Boolean,
        val canSpeedDown: Boolean,
        val world: World,
        val showGrid: Boolean,
        val history: List<World>
    )

    sealed class Output {
        data object GoBack : Output()
    }
}