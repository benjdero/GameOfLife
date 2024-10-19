package com.benjdero.gameoflife

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.draw.DrawComponent
import com.benjdero.gameoflife.game.GameComponent
import com.benjdero.gameoflife.load.LoadComponent
import com.benjdero.gameoflife.menu.MenuComponent
import com.benjdero.gameoflife.save.SaveComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Menu(val component: MenuComponent) : Child()
        data class Draw(val component: DrawComponent) : Child()
        data class Load(val component: LoadComponent) : Child()
        data class Save(val component: SaveComponent) : Child()
        data class Game(val component: GameComponent) : Child()
    }
}