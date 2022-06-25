package com.benjdero.gameoflife

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.World.Model
import com.benjdero.gameoflife.WorldStore.Intent

class WorldComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : World, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            WorldStoreProvider(
                storeFactory = storeFactory
            ).provide()
        }

    override val models: Value<Model> = store.asValue().map {
        Model(
            width = it.width,
            height = it.height,
            world = it.world
        )
    }

    override fun nextGeneration() {
        store.accept(Intent.NextGeneration)
    }
}