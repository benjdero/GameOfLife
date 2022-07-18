package com.benjdero.gameoflife.menu

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.asValue
import com.benjdero.gameoflife.menu.Menu.Model
import com.benjdero.gameoflife.menu.Menu.Output

class MenuComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) : Menu, ComponentContext by componentContext {

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

    override fun onStart() {
        output(Output.Start)
    }
}