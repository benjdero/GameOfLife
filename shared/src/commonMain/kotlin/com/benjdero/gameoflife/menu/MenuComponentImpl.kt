package com.benjdero.gameoflife.menu

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.asValue
import com.benjdero.gameoflife.menu.MenuComponent.Model
import com.benjdero.gameoflife.menu.MenuComponent.Output

class MenuComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) : MenuComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            MenuStoreProvider(
                storeFactory = storeFactory
            ).provide()
        }

    override val models: Value<Model> = store.asValue().map {
        Model(
            unused = it.unused
        )
    }

    override fun onStartDraw() {
        output(Output.StartDraw)
    }

    override fun onStartGame() {
        output(Output.StartGame)
    }
}