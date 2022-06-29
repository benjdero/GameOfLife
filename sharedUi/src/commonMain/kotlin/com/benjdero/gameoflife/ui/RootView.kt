package com.benjdero.gameoflife.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.benjdero.gameoflife.Greeting
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.ui.theme.MyColor
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

private const val PADDING_HORIZONTAL = 1f
private const val PADDING_VERTICAL = 1f

@Composable
fun WorldView(component: World, modifier: Modifier) {
    val model: World.Model by component.models.subscribeAsState()
    val cellColor: Color = MaterialTheme.colors.secondary

    Canvas(
        modifier = modifier
    ) {
        drawRect(
            color = MyColor.BlueGrey50,
            topLeft = Offset.Zero,
            size = size
        )

        val cellWidth: Float = size.width / model.width
        val cellHeight: Float = size.height / model.height

        model.world.forEachIndexed { r: Int, row: Array<Boolean> ->
            row.forEachIndexed { c: Int, cell: Boolean ->
                drawRect(
                    color = if (cell) cellColor else Color.White,
                    topLeft = Offset(
                        x = c * cellWidth + PADDING_HORIZONTAL / 2,
                        y = r * cellHeight + PADDING_VERTICAL / 2
                    ),
                    size = Size(
                        width = cellWidth - PADDING_HORIZONTAL,
                        height = cellHeight - PADDING_VERTICAL
                    )
                )
            }
        }
    }
}

@Composable
fun ControlView(component: World, modifier: Modifier) {
    val model: World.Model by component.models.subscribeAsState()

    BottomAppBar(
        modifier = modifier
    ) {
        IconButton(
            onClick = component::runGame
        ) {
            Icon(
                imageVector = if (model.running)
                    Icons.Default.Pause
                else
                    Icons.Default.PlayArrow,
                contentDescription = if (model.running)
                    "pause"
                else
                    "run",
            )
        }
        IconButton(
            onClick = component::nextStep,
            enabled = !model.running
        ) {
            Icon(
                imageVector = Icons.Default.SkipNext,
                contentDescription = "next"
            )
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Text(
            modifier = Modifier.padding(
                horizontal = 16.dp
            ),
            text = greet()
        )
    }
}

fun greet(): String {
    return Greeting().greeting()
}