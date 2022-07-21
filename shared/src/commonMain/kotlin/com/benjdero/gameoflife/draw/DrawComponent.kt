package com.benjdero.gameoflife.draw

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.asValue
import com.benjdero.gameoflife.draw.Draw.Model
import com.benjdero.gameoflife.draw.Draw.Output
import com.benjdero.gameoflife.draw.DrawStore.Intent

class DrawComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) : Draw, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            DrawStoreProvider(
                storeFactory = storeFactory
            ).provide()
        }

    override val models: Value<Model> = store.asValue().map {
        Model(
            world = it.world
        )
    }

    override fun onDraw(x: Int, y: Int) {
        store.accept(Intent.OnDraw(x, y))
    }

    override fun onDrawValue(x: Int, y: Int, cell: Boolean) {
        store.accept(Intent.OnDrawValue(x, y, cell))
    }

    override fun decreaseWidth() {
        store.accept(Intent.DecreaseWidth)
    }

    override fun increaseWidth() {
        store.accept(Intent.IncreaseWidth)
    }

    override fun decreaseHeight() {
        store.accept(Intent.DecreaseHeight)
    }

    override fun increaseHeight() {
        store.accept(Intent.IncreaseHeight)
    }

    override fun finish() {
        output(Output.Finish(store.state.world))
    }
}