package com.benjdero.gameoflife

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.Game.Model
import com.benjdero.gameoflife.GameStore.Intent

class GameComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : Game, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            GameStoreProvider(
                storeFactory = storeFactory
            ).provide()
        }

    override val models: Value<Model> = store.asValue().map {
        Model(
            running = it.running,
            width = it.width,
            height = it.height,
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