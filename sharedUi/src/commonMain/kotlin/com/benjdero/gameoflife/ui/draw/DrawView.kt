package com.benjdero.gameoflife.ui.draw

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.ArrowCircleDown
import androidx.compose.material.icons.outlined.ArrowCircleLeft
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material.icons.outlined.ArrowCircleUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.benjdero.gameoflife.draw.Draw
import com.benjdero.gameoflife.ui.common.ToggleGridButton
import com.benjdero.gameoflife.ui.theme.MyTheme
import kotlin.math.min

@Composable
fun DrawView(component: Draw) {

    fun getCellFromOffset(canvasSize: IntSize, worldWidth: Int, worldHeight: Int, offset: Offset): IntOffset {
        val cellWidth: Float = canvasSize.width.toFloat() / worldWidth
        val cellHeight: Float = canvasSize.height.toFloat() / worldHeight
        val cellSize: Float = min(cellWidth, cellHeight)
        val x: Int = (offset.x / cellSize).toInt()
        val y: Int = (offset.y / cellSize).toInt()
        return IntOffset(x, y)
    }

    val model: Draw.Model by component.models.subscribeAsState()
    var firstCellDragValue: Boolean by remember { mutableStateOf(false) }

    MyTheme {
        Scaffold(
            backgroundColor = androidx.compose.ui.graphics.Color.Black,
            bottomBar = {
                BottomAppBar {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    ToggleGridButton(
                        showGrid = model.showGrid,
                        toggleGrid = component::toggleGrid
                    )
                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )
                    IconButton(
                        onClick = component::finish
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                    }
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
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset: Offset ->
                                    val cellPosition: IntOffset = getCellFromOffset(size, model.world.width, model.world.height, offset)
                                    firstCellDragValue = model.world.get(cellPosition.x, cellPosition.y)
                                    component.onDrawValue(cellPosition.x, cellPosition.y, !firstCellDragValue)
                                },
                                onDrag = { change: PointerInputChange, dragAmount: Offset ->
                                    val previousCellPosition: IntOffset =
                                        getCellFromOffset(size, model.world.width, model.world.height, change.previousPosition)
                                    val currentCellPosition: IntOffset = getCellFromOffset(size, model.world.width, model.world.height, change.position)
                                    if (previousCellPosition != currentCellPosition)
                                        component.onDrawValue(currentCellPosition.x, currentCellPosition.y, !firstCellDragValue)
                                }
                            )
                        }
                        .pointerInput(Unit) {
                            detectTapGestures { offset: Offset ->
                                val cellPosition: IntOffset = getCellFromOffset(size, model.world.width, model.world.height, offset)
                                component.onDraw(cellPosition.x, cellPosition.y)
                            }
                        }
                ) {
                    CellGridView(model)
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    ) {
                        WorldSizeButton(
                            imageVector = Icons.Outlined.ArrowCircleLeft,
                            onClick = component::increaseLeft
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        WorldSizeButton(
                            imageVector = Icons.Outlined.ArrowCircleRight,
                            onClick = component::decreaseLeft,
                            enabled = model.allowDecreaseWidth
                        )
                    }
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 16.dp)
                    ) {
                        WorldSizeButton(
                            imageVector = Icons.Outlined.ArrowCircleUp,
                            onClick = component::increaseTop
                        )
                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )
                        WorldSizeButton(
                            imageVector = Icons.Outlined.ArrowCircleDown,
                            onClick = component::decreaseTop,
                            enabled = model.allowDecreaseHeight
                        )
                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                    ) {
                        WorldSizeButton(
                            imageVector = Icons.Outlined.ArrowCircleRight,
                            onClick = component::increaseRight
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        WorldSizeButton(
                            imageVector = Icons.Outlined.ArrowCircleLeft,
                            onClick = component::decreaseRight,
                            enabled = model.allowDecreaseWidth
                        )
                    }
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                    ) {
                        WorldSizeButton(
                            imageVector = Icons.Outlined.ArrowCircleDown,
                            onClick = component::increaseBottom
                        )
                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )
                        WorldSizeButton(
                            imageVector = Icons.Outlined.ArrowCircleUp,
                            onClick = component::decreaseBottom,
                            enabled = model.allowDecreaseHeight
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WorldSizeButton(imageVector: ImageVector, onClick: () -> Unit, enabled: Boolean = true) {
    Button(
        modifier = Modifier.size(36.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(all = 0.dp),
        enabled = enabled,
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
}