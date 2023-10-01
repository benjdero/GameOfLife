package com.benjdero.gameoflife.draw

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.draw.Draw.Model
import com.benjdero.gameoflife.mockWorld

class DrawMock : Draw {
    override val models: Value<Model> = MutableValue(
        initialValue = Model(
            world = mockWorld(),
            showGrid = false,
            allowDecreaseWidth = true,
            allowDecreaseHeight = true
        )
    )

    override fun onDraw(x: Int, y: Int) {}
    override fun onDrawValue(x: Int, y: Int, cell: Boolean) {}
    override fun clearWorld() {}
    override fun randomWorld() {}
    override fun toggleGrid() {}
    override fun increaseLeft() {}
    override fun decreaseLeft() {}
    override fun increaseTop() {}
    override fun decreaseTop() {}
    override fun increaseRight() {}
    override fun decreaseRight() {}
    override fun increaseBottom() {}
    override fun decreaseBottom() {}
    override fun load() {}
    override fun save(world: World) {}
    override fun finish() {}
    override fun goBack() {}
}