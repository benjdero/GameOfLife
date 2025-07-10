package com.benjdero.gameoflife.game

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.benjdero.gameoflife.common.CellGridView
import com.benjdero.gameoflife.game.GameComponent.Model
import kotlin.math.max
import kotlin.math.sqrt

@Composable
fun GameView(
    component: GameComponent
) {
    val model: Model by component.models.subscribeAsState()
    var scale: Float by remember { mutableStateOf(1f) }
    var offset: Offset by remember { mutableStateOf(Offset.Zero) }
    var viewSize: IntSize by remember { mutableStateOf(IntSize.Zero) }

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

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary
            ) {
                ControlView(
                    model = model,
                    goBack = component::goBack,
                    prevStep = component::prevStep,
                    runGame = component::runGame,
                    nextStep = component::nextStep,
                    canSpeedUp = model.canSpeedUp,
                    canSpeedDown = model.canSpeedDown,
                    speedUp = component::speedUp,
                    speedDown = component::speedDown,
                    showGrid = model.showGrid,
                    toggleGrid = component::toggleGrid,
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
                CellGridView(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (model.showGrid)
                                MaterialTheme.colors.onBackground
                            else
                                MaterialTheme.colors.background
                        ),
                    showCursor = false,
                    world = model.world
                )
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