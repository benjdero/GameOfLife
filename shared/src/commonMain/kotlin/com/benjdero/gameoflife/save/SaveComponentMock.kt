package com.benjdero.gameoflife.save

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.model.mockWorld
import com.benjdero.gameoflife.save.SaveComponent.Model

class SaveComponentMock : SaveComponent {
    override val models: Value<Model> = MutableValue(
        initialValue = Model(
            name = "World 1",
            world = mockWorld(),
            canSave = true,
            saved = false
        )
    )

    override fun setName(name: String) {}
    override fun clearName() {}
    override fun save() {}
    override fun exit() {}
}