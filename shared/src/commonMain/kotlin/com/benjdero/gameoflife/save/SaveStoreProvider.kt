package com.benjdero.gameoflife.save

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.model.dao.DaoService
import com.benjdero.gameoflife.save.SaveStore.Action
import com.benjdero.gameoflife.save.SaveStore.Intent
import com.benjdero.gameoflife.save.SaveStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SaveStoreProvider(
    private val storeFactory: StoreFactory,
    private val daoService: DaoService,
    private val world: World
) {
    fun provide(): SaveStore =
        object : SaveStore, Store<Intent, State, Action> by storeFactory.create(
            name = "SaveStore",
            initialState = State(
                world = world
            ),
            bootstrapper = SimpleBootstrapper(
                actions = arrayOf(
                    Action.Initialize
                )
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class NameUpdate(val name: String) : Msg()
        data class Saved(val saved: Boolean) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Nothing>() {
        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.Initialize -> initialize()
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.SetName -> setName(intent.name)
                Intent.ClearName -> clearName()
                Intent.Save -> save(getState())
            }
        }

        private fun initialize() {
            scope.launch(Dispatchers.IO) {
                val worldId: Long = daoService.maxWorldId()?.plus(1) ?: 1
                val name: String = "World $worldId"
                withContext(Dispatchers.Main) {
                    dispatch(Msg.NameUpdate(name))
                }
            }
        }

        private fun setName(name: String) {
            dispatch(Msg.NameUpdate(name))
        }

        private fun clearName() {
            dispatch(Msg.NameUpdate(""))
        }

        private fun save(state: State) {
            scope.launch(Dispatchers.IO) {
                daoService.saveWorld(state.world, state.name)
                withContext(Dispatchers.Main) {
                    dispatch(Msg.Saved(true))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.NameUpdate -> copy(
                    name = msg.name,
                    canSave = msg.name.isNotEmpty()
                )
                is Msg.Saved -> copy(saved = msg.saved)
            }
    }
}