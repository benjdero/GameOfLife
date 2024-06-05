package com.benjdero.gameoflife.menu

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.benjdero.gameoflife.menu.MenuStore.Intent
import com.benjdero.gameoflife.menu.MenuStore.State

internal class MenuStoreProvider(
    private val storeFactory: StoreFactory
) {
    fun provide(): MenuStore =
        object : MenuStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "MenuStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {

        override fun executeIntent(intent: Intent) {}
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            this
    }
}