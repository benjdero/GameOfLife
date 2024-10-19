package com.benjdero.gameoflife.load

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.load.LoadComponent.Model
import com.benjdero.gameoflife.model.World
import com.benjdero.gameoflife.model.mockWorld

class LoadComponentMock : LoadComponent {
    override val models: Value<Model> = MutableValue(
        initialValue = Model(
            worldList = listOf(
                mockWorld(),
                mockWorld()
            )
        )
    )

    override fun onWorldSelected(world: World) {}
    override fun deleteWorld(world: World) {}
    override fun goBack() {}
}