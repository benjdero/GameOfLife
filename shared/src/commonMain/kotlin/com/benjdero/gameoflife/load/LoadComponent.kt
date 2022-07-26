package com.benjdero.gameoflife.load

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.asValue
import com.benjdero.gameoflife.load.Load.Model
import com.benjdero.gameoflife.load.Load.Output
import com.benjdero.gameoflife.model.dao.DaoService

class LoadComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    daoService: DaoService,
    private val output: (Output) -> Unit
) : Load, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            LoadStoreProvider(
                storeFactory = storeFactory,
                daoService = daoService
            ).provide()
        }

    override val models: Value<Model> = store.asValue().map {
        Model(
            worldList = it.worldList
        )
    }

    override fun onWorldSelected(world: World) {
        output(Output.WorldSelected(world))
    }
}