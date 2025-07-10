package com.benjdero.gameoflife

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.benjdero.gameoflife.RootComponent.Child
import com.benjdero.gameoflife.draw.DrawView
import com.benjdero.gameoflife.game.GameView
import com.benjdero.gameoflife.load.LoadView
import com.benjdero.gameoflife.menu.MenuView
import com.benjdero.gameoflife.save.SaveView
import com.benjdero.gameoflife.theme.MyTheme

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