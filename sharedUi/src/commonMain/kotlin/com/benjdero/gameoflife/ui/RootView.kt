package com.benjdero.gameoflife.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.benjdero.gameoflife.Greeting
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.ui.theme.MyTheme

@Composable
fun RootView(component: World) {
    MyTheme {
        Column {
            WorldView(
                component = component,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            ControlView(
                component = component,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun WorldView(component: World, modifier: Modifier) {
    val model: World.Model by component.models.subscribeAsState()

    Canvas(
        modifier = modifier
    ) {
        val cellWidth: Float = size.width / model.width
        val cellHeight: Float = size.height / model.height

        model.world.forEachIndexed { r: Int, row: Array<Boolean> ->
            row.forEachIndexed { c: Int, cell: Boolean ->
                drawRect(
                    color = if (cell) Color.Black else Color.White,
                    topLeft = Offset(
                        x = c * cellWidth,
                        y = r * cellHeight
                    ),
                    size = Size(
                        width = cellWidth,
                        height = cellHeight
                    )
                )
            }
        }
    }
}

@Composable
fun ControlView(component: World, modifier: Modifier) {
    Row {
        Button(
            onClick = component::nextGeneration
        ) {
            Text(
                text = "Next"
            )
        }
        Text(
            modifier = modifier,
            text = greet()
        )
    }
}

fun greet(): String {
    return Greeting().greeting()
}