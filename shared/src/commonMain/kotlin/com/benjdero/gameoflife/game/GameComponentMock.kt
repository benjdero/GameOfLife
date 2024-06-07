package com.benjdero.gameoflife.game

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.game.GameComponent.Model
import com.benjdero.gameoflife.model.Speed
import com.benjdero.gameoflife.model.mockWorld

class GameComponentMock : GameComponent {
    override val models: Value<Model> = MutableValue(
        initialValue = Model(
            running = false,
            generation = 0,
            speed = Speed.FAST_2X,
            canSpeedUp = true,
            canSpeedDown = true,
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
    override fun speedUp() {}
    override fun speedDown() {}
}