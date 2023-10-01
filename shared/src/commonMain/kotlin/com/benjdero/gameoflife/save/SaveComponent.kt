package com.benjdero.gameoflife.save

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.asValue
import com.benjdero.gameoflife.model.dao.DaoService
import com.benjdero.gameoflife.save.Save.Model
import com.benjdero.gameoflife.save.Save.Output
import com.benjdero.gameoflife.save.SaveStore.Intent

class SaveComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    daoService: DaoService,
    world: World,
    private val output: (Output) -> Unit
) : Save, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            SaveStoreProvider(
                storeFactory = storeFactory,
                daoService = daoService,
                world = world
            ).provide()
        }

    override val models: Value<Model> = store.asValue().map {
        Model(
            name = it.name,
            world = it.world,
            canSave = it.canSave,
            saved = it.saved
        )
    }

    override fun setName(name: String) {
        store.accept(Intent.SetName(name))
    }

    override fun clearName() {
        store.accept(Intent.ClearName)
    }

    override fun save() {
        store.accept(Intent.Save)
    }

    override fun exit() {
        output(Output.GoBack)
    }
}