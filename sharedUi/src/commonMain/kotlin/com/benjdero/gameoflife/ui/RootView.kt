package com.benjdero.gameoflife.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.benjdero.gameoflife.Greeting
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.ui.theme.MyColor
import com.benjdero.gameoflife.ui.theme.MyTheme
import kotlin.math.min

@Composable
fun RootView(component: World) {
    MyTheme {
        Column {
            WorldView(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                component = component
            )
            ControlView(
                modifier = Modifier
                    .fillMaxWidth(),
                component = component
            )
        }
    }
}

private const val PADDING_HORIZONTAL = 1f
private const val PADDING_VERTICAL = 1f

@Composable
fun WorldView(modifier: Modifier, component: World) {
    val model: World.Model by component.models.subscribeAsState()
    val cellColor: Color = MaterialTheme.colors.secondary

    var scale: Float by remember { mutableStateOf(1f) }
    var offset: Offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange: Float, panChange: Offset, _: Float ->
        scale *= zoomChange
        offset += panChange
    }

    Box(
        modifier = modifier.then(
            Modifier
                .background(MyColor.BlueGrey50)
                .transformable(state)
        )
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val cellWidth: Float = size.width / model.width
                val cellHeight: Float = size.height / model.height
                val cellSize: Float = min(cellWidth, cellHeight)

                model.world.forEachIndexed { r: Int, row: Array<Boolean> ->
                    row.forEachIndexed { c: Int, cell: Boolean ->
                        drawRect(
                            color = if (cell) cellColor else Color.White,
                            topLeft = Offset(
                                x = c * cellSize + PADDING_HORIZONTAL / 2 + offset.x,
                                y = r * cellSize + PADDING_VERTICAL / 2 + offset.y
                            ),
                            size = Size(
                                width = cellSize - PADDING_HORIZONTAL,
                                height = cellSize - PADDING_VERTICAL
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ControlView(modifier: Modifier, component: World) {
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