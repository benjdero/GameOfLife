package com.benjdero.gameoflife.game

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.asValue
import com.benjdero.gameoflife.game.Game.Model
import com.benjdero.gameoflife.game.GameStore.Intent

class GameComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    world: World
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
            world = it.world
        )
    }

    override fun runGame() {
        store.accept(Intent.RunGame)
    }

    override fun nextStep() {
        store.accept(Intent.NextStep)
    }
}