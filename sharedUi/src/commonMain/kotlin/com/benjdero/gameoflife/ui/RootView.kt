package com.benjdero.gameoflife.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.benjdero.gameoflife.RootComponent
import com.benjdero.gameoflife.RootComponent.Child
import com.benjdero.gameoflife.ui.draw.DrawView
import com.benjdero.gameoflife.ui.game.GameView
import com.benjdero.gameoflife.ui.load.LoadView
import com.benjdero.gameoflife.ui.menu.MenuView
import com.benjdero.gameoflife.ui.save.SaveView
import com.benjdero.gameoflife.ui.theme.MyTheme

@Composable
fun RootView(
    component: RootComponent
) {
    MyTheme {
        Children(
            stack = component.childStack
        ) {
            when (val child: Child = it.instance) {
                is Child.Menu -> MenuView(child.component)
                is Child.Draw -> DrawView(child.component)
                is Child.Load -> LoadView(child.component)
                is Child.Save -> SaveView(child.component)
                is Child.Game -> GameView(child.component)
            }
        }
    }
}