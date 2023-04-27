package com.benjdero.gameoflife.load

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.load.LoadStore.Action
import com.benjdero.gameoflife.load.LoadStore.Intent
import com.benjdero.gameoflife.load.LoadStore.State
import com.benjdero.gameoflife.model.dao.DaoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class LoadStoreProvider(
    private val storeFactory: StoreFactory,
    private val daoService: DaoService
) {
    fun provide(): LoadStore =
        object : LoadStore, Store<Intent, State, Action> by storeFactory.create(
            name = "LoadStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(
                actions = arrayOf(
                    Action.Initialize
                )
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class WorldListUpdate(val worldList: List<World>) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Nothing>() {
        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.Initialize -> initialize()
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.DeleteWorld -> deleteWorld(intent.id, getState())
            }
        }

        private fun initialize() {
            scope.launch {
                val worldList: List<World> = daoService.findAllWorld()
                withContext(Dispatchers.Main) {
                    dispatch(Msg.WorldListUpdate(worldList))
                }
            }
        }

        private fun deleteWorld(id: Long, state: State) {
            scope.launch {
                daoService.deleteById(id)
                val worldList: List<World> = state.worldList.filterNot { world: World ->
                    world.saved is World.Saved.AsWorld && world.saved.id == id
                }
                withContext(Dispatchers.Main) {
                    dispatch(Msg.WorldListUpdate(worldList))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.WorldListUpdate -> copy(worldList = msg.worldList)
            }
    }
}