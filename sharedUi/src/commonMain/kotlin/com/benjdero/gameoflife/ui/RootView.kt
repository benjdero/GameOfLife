package com.benjdero.gameoflife.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.benjdero.gameoflife.Root
import com.benjdero.gameoflife.ui.game.GameView

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootView(component: Root) {
    MaterialTheme {
        Children(
            routerState = component.routerState
        ) {
            val child: Root.Child = it.instance
            when (child) {
                is Root.Child.ChildGame -> GameView(child.component)
            }
        }
    }
}