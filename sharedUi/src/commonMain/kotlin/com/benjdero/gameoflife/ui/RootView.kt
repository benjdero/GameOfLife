package com.benjdero.gameoflife.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.ui.theme.MyTheme
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sqrt

private const val PADDING_HORIZONTAL = 1f
private const val PADDING_VERTICAL = 1f

@Composable
fun RootView(component: World) {
    val model: World.Model by component.models.subscribeAsState()
    val cellColor: Color = MaterialTheme.colors.secondary
    var scale: Float by remember { mutableStateOf(1f) }
    var offset: Offset by remember { mutableStateOf(Offset.Zero) }
    var viewSize by remember { mutableStateOf(IntSize.Zero) }

    MyTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    cutoutShape = CircleShape
                ) {
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
                    IconButton(
                        onClick = {
                            scale = max(scale - 0.5f, 1f)
                            val maxXOffset: Float = viewSize.width * (scale - 1f) / 2f
                            val maxYOffset: Float = viewSize.height * (scale - 1f) / 2f
                            offset = Offset(
                                x = offset.x.coerceIn(-maxXOffset, maxXOffset),
                                y = offset.y.coerceIn(-maxYOffset, maxYOffset),
                            )
                        },
                        enabled = scale > 1f
                    ) {
                        Icon(
                            imageVector = Icons.Default.ZoomOut,
                            contentDescription = "zoomOut"
                        )
                    }
                    Text(
                        text = "${(scale * 100).roundToInt()}%"
                    )
                    IconButton(
                        onClick = {
                            scale += 0.5f
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ZoomIn,
                            contentDescription = "zoomIn"
                        )
                    }
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(
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
            }
        ) { scaffoldPadding: PaddingValues ->
            Column(
                modifier = Modifier.padding(scaffoldPadding)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .onSizeChanged {
                            viewSize = it
                        }
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .pointerInput(Unit) {
                            forEachGesture {
                                awaitPointerEventScope {
                                    awaitFirstDown(requireUnconsumed = false)
                                    do {
                                        val event = awaitPointerEvent()
                                        when (event.changes.size) {
                                            1 -> {
                                                // Drag
                                                val delta: Offset = event.changes[0].position - event.changes[0].previousPosition
                                                val newOffset: Offset = offset + delta
                                                // Make sure the view isn't dragged past its content
                                                val maxXOffset: Float = size.width * (scale - 1f) / 2f
                                                val maxYOffset: Float = size.height * (scale - 1f) / 2f
                                                offset = Offset(
                                                    x = newOffset.x.coerceIn(-maxXOffset, maxXOffset),
                                                    y = newOffset.y.coerceIn(-maxYOffset, maxYOffset),
                                                )
                                            }
                                            2 -> {
                                                // Zoom
                                                val previousDelta: Offset = event.changes[0].previousPosition - event.changes[1].previousPosition
                                                val currentDelta: Offset = event.changes[0].position - event.changes[1].position
                                                val zoom: Float = sqrt(currentDelta.getDistanceSquared() / previousDelta.getDistanceSquared())
                                                scale = max(scale * zoom, 1f)
                                                val maxXOffset: Float = viewSize.width * (scale - 1f) / 2f
                                                val maxYOffset: Float = viewSize.height * (scale - 1f) / 2f
                                                offset = Offset(
                                                    x = offset.x.coerceIn(-maxXOffset, maxXOffset),
                                                    y = offset.y.coerceIn(-maxYOffset, maxYOffset),
                                                )
                                            }
                                        }
                                    } while (event.changes.any { it.pressed })
                                }
                            }
                        }
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
                                        x = c * cellSize + PADDING_HORIZONTAL / 2,
                                        y = r * cellSize + PADDING_VERTICAL / 2
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
    }
}
