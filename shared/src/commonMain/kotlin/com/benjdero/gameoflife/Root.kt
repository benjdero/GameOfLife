package com.benjdero.gameoflife

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.draw.Draw
import com.benjdero.gameoflife.game.Game
import com.benjdero.gameoflife.load.Load
import com.benjdero.gameoflife.menu.Menu
import com.benjdero.gameoflife.save.Save

interface Root {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class ChildMenu(val component: Menu) : Child()
        data class ChildDraw(val component: Draw) : Child()
        data class ChildLoad(val component: Load) : Child()
        data class ChildSave(val component: Save) : Child()
        data class ChildGame(val component: Game) : Child()
    }
}