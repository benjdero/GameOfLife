package com.benjdero.gameoflife.draw

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.asValue
import com.benjdero.gameoflife.draw.DrawComponent.Model
import com.benjdero.gameoflife.draw.DrawComponent.Output
import com.benjdero.gameoflife.draw.DrawStore.Intent
import com.benjdero.gameoflife.model.World

class DrawComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    world: World?,
    private val output: (Output) -> Unit
) : DrawComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            DrawStoreProvider(
                storeFactory = storeFactory,
                world = world
            ).provide()
        }

    override val models: Value<Model> = store.asValue().map {
        Model(
            world = it.world,
            showGrid = it.showGrid,
            allowDecreaseWidth = it.allowDecreaseWidth,
            allowDecreaseHeight = it.allowDecreaseHeight
        )
    }

    override fun onDraw(x: Int, y: Int) {
        store.accept(Intent.OnDraw(x, y))
    }

    override fun onDrawValue(x: Int, y: Int, cell: Boolean) {
        store.accept(Intent.OnDrawValue(x, y, cell))
    }

    override fun clearWorld() {
        store.accept(Intent.ClearWorld)
    }

    override fun randomWorld() {
        store.accept(Intent.RandomWorld)
    }

    override fun toggleGrid() {
        store.accept(Intent.ShowGrid)
    }

    override fun increaseLeft() {
        store.accept(Intent.IncreaseLeft)
    }

    override fun decreaseLeft() {
        store.accept(Intent.DecreaseLeft)
    }

    override fun increaseTop() {
        store.accept(Intent.IncreaseTop)
    }

    override fun decreaseTop() {
        store.accept(Intent.DecreaseTop)
    }

    override fun increaseRight() {
        store.accept(Intent.IncreaseRight)
    }

    override fun decreaseRight() {
        store.accept(Intent.DecreaseRight)
    }

    override fun increaseBottom() {
        store.accept(Intent.IncreaseBottom)
    }

    override fun decreaseBottom() {
        store.accept(Intent.DecreaseBottom)
    }

    override fun load() {
        output(Output.Load)
    }

    override fun save(world: World) {
        output(Output.Save(world))
    }

    override fun finish() {
        output(Output.Finish(store.state.world))
    }

    override fun goBack() {
        output(Output.GoBack)
    }
}