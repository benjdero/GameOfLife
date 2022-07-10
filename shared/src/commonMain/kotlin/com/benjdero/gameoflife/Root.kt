package com.benjdero.gameoflife

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.game.Game

interface Root {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class ChildGame(val component: Game) : Child()
    }
}