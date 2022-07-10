package com.benjdero.gameoflife.ui.game

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.benjdero.gameoflife.Game
import com.benjdero.gameoflife.Game.Model
import com.benjdero.gameoflife.ui.theme.MyTheme
import kotlin.math.max
import kotlin.math.sqrt

@Composable
fun GameView(component: Game) {
    val model: Model by component.models.subscribeAsState()
    var scale: Float by remember { mutableStateOf(1f) }
    var offset: Offset by remember { mutableStateOf(Offset.Zero) }
    var viewSize by remember { mutableStateOf(IntSize.Zero) }

    /**
     * Make sure the view content doesn't go out of bounds when moving or unzooming
     */
    fun coerceInOffset(newOffset: Offset): Offset {
        val maxXOffset: Float = viewSize.width * (scale - 1f) / 2f
        val maxYOffset: Float = viewSize.height * (scale - 1f) / 2f
        return Offset(
            x = newOffset.x.coerceIn(-maxXOffset, maxXOffset),
            y = newOffset.y.coerceIn(-maxYOffset, maxYOffset),
        )
    }

    MyTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    cutoutShape = CircleShape
                ) {
                    ControlView(
                        model = model,
                        nextStep = component::nextStep,
                        scale = scale,
                        setScale = {
                            scale = it
                        },
                        offset = offset,
                        setOffset = {
                            offset = coerceInOffset(it)
                        }
                    )
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
                            detectAllGestures(
                                drag = { drag: Offset ->
                                    offset = coerceInOffset(offset + drag)
                                },
                                zoom = { zoom: Float ->
                                    scale = max(scale * zoom, 1f)
                                    if (zoom < 1f)
                                        offset = coerceInOffset(offset)
                                }
                            )
                        }
                ) {
                    CellGridView(model)
                }
            }
        }
    }
}

suspend fun PointerInputScope.detectAllGestures(drag: (Offset) -> Unit, zoom: (Float) -> Unit) {
    forEachGesture {
        awaitPointerEventScope {
            awaitFirstDown(requireUnconsumed = false)
            do {
                val event = awaitPointerEvent()
                when (event.changes.size) {
                    1 -> {
                        // Drag
                        val dragValue: Offset = event.changes[0].position - event.changes[0].previousPosition
                        drag(dragValue)
                    }
                    2 -> {
                        // Zoom
                        val previousDelta: Offset = event.changes[0].previousPosition - event.changes[1].previousPosition
                        val currentDelta: Offset = event.changes[0].position - event.changes[1].position
                        val zoomValue: Float = sqrt(currentDelta.getDistanceSquared() / previousDelta.getDistanceSquared())
                        zoom(zoomValue)
                    }
                }
            } while (event.changes.any { it.pressed })
        }
    }
}