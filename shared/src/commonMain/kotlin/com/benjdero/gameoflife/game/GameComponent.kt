package com.benjdero.gameoflife.game

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.asValue
import com.benjdero.gameoflife.game.Game.Model
import com.benjdero.gameoflife.game.Game.Output
import com.benjdero.gameoflife.game.GameStore.Intent

class GameComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    world: World?,
    private val output: (Output) -> Unit
) : Game, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            GameStoreProvider(
                storeFactory = storeFactory,
                world = world
            ).provide()
        }

    override val models: Value<Model> = store.asValue().map {
        Model(
            running = it.running,
            generation = it.generation,
            world = it.world,
            showGrid = it.showGrid,
            history = it.history
        )
    }

    override fun runGame() {
        store.accept(Intent.RunGame)
    }

    override fun prevStep() {
        store.accept(Intent.PrevStep)
    }

    override fun nextStep() {
        store.accept(Intent.NextStep)
    }

    override fun toggleGrid() {
        store.accept(Intent.ShowGrid)
    }

    override fun goBack() {
        output(Output.GoBack)
    }
}