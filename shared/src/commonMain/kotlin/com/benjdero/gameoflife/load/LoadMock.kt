package com.benjdero.gameoflife.load

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.load.Load.Model
import com.benjdero.gameoflife.mockWorld

class LoadMock : Load {
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