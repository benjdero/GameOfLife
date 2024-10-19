package com.benjdero.gameoflife.game

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.asValue
import com.benjdero.gameoflife.game.GameComponent.Model
import com.benjdero.gameoflife.game.GameComponent.Output
import com.benjdero.gameoflife.game.GameStore.Intent
import com.benjdero.gameoflife.model.World

class GameComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    world: World?,
    private val output: (Output) -> Unit
) : GameComponent, ComponentContext by componentContext {

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
            speed = it.speed,
            canSpeedUp = it.canSpeedUp,
            canSpeedDown = it.canSpeedDown,
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

    override fun speedUp() {
        store.accept(Intent.SpeedUp)
    }

    override fun speedDown() {
        store.accept(Intent.SpeedDown)
    }
}