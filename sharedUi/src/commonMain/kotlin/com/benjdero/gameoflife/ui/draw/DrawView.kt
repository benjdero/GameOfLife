package com.benjdero.gameoflife.ui.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BorderClear
import androidx.compose.material.icons.filled.BrowserUpdated
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Save
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.benjdero.gameoflife.draw.DrawComponent
import com.benjdero.gameoflife.draw.DrawComponent.Model
import com.benjdero.gameoflife.ui.common.CellGridView
import com.benjdero.gameoflife.ui.common.ToggleGridButton
import kotlin.math.min

@Composable
fun DrawView(
    component: DrawComponent
) {

    fun getCellFromOffset(canvasSize: IntSize, worldWidth: Int, worldHeight: Int, offset: Offset): IntOffset {
        val cellWidth: Float = canvasSize.width.toFloat() / worldWidth
        val cellHeight: Float = canvasSize.height.toFloat() / worldHeight
        val cellSize: Float = min(cellWidth, cellHeight)
        val outsidePaddingHorizontal: Float = canvasSize.width.toFloat() - (worldWidth * cellSize)
        val outsidePaddingVertical: Float = canvasSize.height.toFloat() - (worldHeight * cellSize)
        val x: Int = ((offset.x - outsidePaddingHorizontal / 2) / cellSize).toInt()
        val y: Int = ((offset.y - outsidePaddingVertical / 2) / cellSize).toInt()
        return IntOffset(x, y)
    }

    val model: Model by component.models.subscribeAsState()
    var firstCellDragValue: Boolean by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = Color.Black,
        bottomBar = {
            BottomAppBar {
                IconButton(
                    onClick = component::goBack
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = component::clearWorld
                ) {
                    Icon(
                        imageVector = Icons.Default.BorderClear,
                        contentDescription = null
                    )
                }
                Spacer(
                    modifier = Modifier.width(4.dp)
                )
                IconButton(
                    onClick = component::randomWorld
                ) {
                    Icon(
                        imageVector = Icons.Default.Casino,
                        contentDescription = null
                    )
                }
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                ToggleGridButton(
                    showGrid = model.showGrid,
                    toggleGrid = component::toggleGrid
                )
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
                IconButton(
                    onClick = component::load
                ) {
                    Icon(
                        imageVector = Icons.Default.BrowserUpdated,
                        contentDescription = null
                    )
                }
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                IconButton(
                    onClick = {
                        component.save(model.world)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = null
                    )
                }
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
                                if (!model.world.isWithinBounds(cellPosition.x, cellPosition.y)) {
                                    println("Out of bounds coordinates [${cellPosition.x}:${cellPosition.y}]")
                                    return@detectDragGestures
                                }
                                firstCellDragValue = model.world.isAlive(cellPosition.x, cellPosition.y)
                                component.onDrawValue(cellPosition.x, cellPosition.y, !firstCellDragValue)
                            },
                            onDrag = { change: PointerInputChange, _: Offset ->
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
                CellGridView(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (model.showGrid)
                                MaterialTheme.colors.onBackground
                            else
                                MaterialTheme.colors.background
                        ),
                    showCursor = true,
                    world = model.world
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                ) {
                    Button(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(
                            topStart = 50f,
                            bottomStart = 50f
                        ),
                        contentPadding = PaddingValues(all = 0.dp),
                        onClick = component::increaseLeft
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleLeft,
                            contentDescription = null
                        )
                    }
                    Button(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(
                            topEnd = 50f,
                            bottomEnd = 50f
                        ),
                        contentPadding = PaddingValues(all = 0.dp),
                        enabled = model.allowDecreaseWidth,
                        onClick = component::decreaseLeft
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleRight,
                            contentDescription = null
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                ) {
                    Button(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(
                            topStart = 50f,
                            topEnd = 50f
                        ),
                        contentPadding = PaddingValues(all = 0.dp),
                        onClick = component::increaseTop
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleUp,
                            contentDescription = null
                        )
                    }
                    Button(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(
                            bottomStart = 50f,
                            bottomEnd = 50f
                        ),
                        contentPadding = PaddingValues(all = 0.dp),
                        enabled = model.allowDecreaseHeight,
                        onClick = component::decreaseTop
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleDown,
                            contentDescription = null
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                ) {
                    Button(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(
                            topStart = 50f,
                            bottomStart = 50f
                        ),
                        contentPadding = PaddingValues(all = 0.dp),
                        enabled = model.allowDecreaseWidth,
                        onClick = component::decreaseRight
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleLeft,
                            contentDescription = null
                        )
                    }
                    Button(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(
                            topEnd = 50f,
                            bottomEnd = 50f
                        ),
                        contentPadding = PaddingValues(all = 0.dp),
                        onClick = component::increaseRight
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleRight,
                            contentDescription = null
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    Button(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(
                            topStart = 50f,
                            topEnd = 50f
                        ),
                        contentPadding = PaddingValues(all = 0.dp),
                        enabled = model.allowDecreaseHeight,
                        onClick = component::decreaseBottom
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleUp,
                            contentDescription = null
                        )
                    }
                    Button(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(
                            bottomStart = 50f,
                            bottomEnd = 50f
                        ),
                        contentPadding = PaddingValues(all = 0.dp),
                        onClick = component::increaseBottom
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleDown,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}
