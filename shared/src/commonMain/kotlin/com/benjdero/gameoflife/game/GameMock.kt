package com.benjdero.gameoflife.game

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.game.Game.Model
import com.benjdero.gameoflife.mockWorld

class GameMock : Game {
    override val models: Value<Model> = MutableValue(
        initialValue = Model(
            running = false,
            generation = 0,
            world = mockWorld(),
            showGrid = false,
            history = emptyList()
        )
    )

    override fun runGame() {}
    override fun prevStep() {}
    override fun nextStep() {}
    override fun toggleGrid() {}
    override fun goBack() {}
}