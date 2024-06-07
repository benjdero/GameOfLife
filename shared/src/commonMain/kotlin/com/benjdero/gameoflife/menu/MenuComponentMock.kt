package com.benjdero.gameoflife.menu

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.menu.MenuComponent.Model

class MenuComponentMock : MenuComponent {
    override val models: Value<Model> = MutableValue(
        initialValue = Model(
            unused = 0
        )
    )

    override fun onStartDraw() {}
    override fun onStartGame() {}
}