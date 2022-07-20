package com.benjdero.gameoflife.ui.draw

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.benjdero.gameoflife.draw.Draw
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
        Scaffold { scaffoldPadding: PaddingValues ->
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
                                    val cellPosition: IntOffset = getCellFromOffset(size, model.width, model.height, offset)
                                    firstCellDragValue = model.world[cellPosition.y][cellPosition.x]
                                    component.onDrawValue(cellPosition.x, cellPosition.y, !firstCellDragValue)
                                },
                                onDrag = { change: PointerInputChange, dragAmount: Offset ->
                                    val previousCellPosition: IntOffset = getCellFromOffset(size, model.width, model.height, change.previousPosition)
                                    val currentCellPosition: IntOffset = getCellFromOffset(size, model.width, model.height, change.position)
                                    if (previousCellPosition != currentCellPosition)
                                        component.onDrawValue(currentCellPosition.x, currentCellPosition.y, !firstCellDragValue)
                                }
                            )
                        }
                        .pointerInput(Unit) {
                            detectTapGestures { offset: Offset ->
                                val cellPosition: IntOffset = getCellFromOffset(size, model.width, model.height, offset)
                                component.onDraw(cellPosition.x, cellPosition.y)
                            }
                        }
                ) {
                    CellGridView(model)
                }
            }
        }
    }
}