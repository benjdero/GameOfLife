package com.benjdero.gameoflife

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.game.Game
import com.benjdero.gameoflife.menu.Menu

interface Root {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class ChildGame(val component: Game) : Child()
        data class ChildMenu(val component: Menu) : Child()
    }
}