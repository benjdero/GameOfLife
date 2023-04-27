package com.benjdero.gameoflife.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.benjdero.gameoflife.Root
import com.benjdero.gameoflife.ui.draw.DrawView
import com.benjdero.gameoflife.ui.game.GameView
import com.benjdero.gameoflife.ui.load.LoadView
import com.benjdero.gameoflife.ui.menu.MenuView

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootView(component: Root) {
    MaterialTheme {
        Children(
            stack = component.childStack
        ) {
            val child: Root.Child = it.instance
            when (child) {
                is Root.Child.ChildMenu -> MenuView(child.component)
                is Root.Child.ChildDraw -> DrawView(child.component)
                is Root.Child.ChildLoad -> LoadView(child.component)
                is Root.Child.ChildGame -> GameView(child.component)
            }
        }
    }
}