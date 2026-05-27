package com.benjdero.gameoflife.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.IntOffset
import com.benjdero.gameoflife.model.World
import kotlin.math.min

private const val PADDING_HORIZONTAL = 1f
private const val PADDING_VERTICAL = 1f

@Composable
internal fun CellGridView(
    modifier: Modifier = Modifier,
    showCursor: Boolean,
    world: World
) {
    val aliveCellColor: Color = MaterialTheme.colors.secondary
    val deadCellColor: Color = MaterialTheme.colors.background
    val cursorColor: Color = MaterialTheme.colors.error
    val (cursorPosition, setCursorPosition) = remember { mutableStateOf<Offset?>(null) }

    Canvas(
        modifier = modifier
            .onPointerMoveEvent(
                showCursor = showCursor,
                setCursorPosition = setCursorPosition
            )
    ) {
        val cellWidth: Float = size.width / world.width
        val cellHeight: Float = size.height / world.height
        val cellSize: Float = min(cellWidth, cellHeight)
        val outsidePaddingHorizontal: Float = size.width - (world.width * cellSize)
        val outsidePaddingVertical: Float = size.height - (world.height * cellSize)
        val cursorCell: IntOffset? = if (cursorPosition != null) {
            IntOffset(
                x = ((cursorPosition.x - outsidePaddingHorizontal / 2) / cellSize).toInt(),
                y = ((cursorPosition.y - outsidePaddingVertical / 2) / cellSize).toInt(),
            )
        } else {
            null
        }

        world.forEachIndexed { x: Int, y: Int, cell: Boolean ->
            drawRoundRect(
                color = if (cell) aliveCellColor else deadCellColor,
                topLeft = Offset(
                    x = x * cellSize + (PADDING_HORIZONTAL + outsidePaddingHorizontal) / 2,
                    y = y * cellSize + (PADDING_VERTICAL + outsidePaddingVertical) / 2
                ),
                size = Size(
                    width = cellSize - PADDING_HORIZONTAL,
                    height = cellSize - PADDING_VERTICAL
                ),
                cornerRadius = CornerRadius(
                    x = cellSize / 8,
                    y = cellSize / 8
                )
            )
        }
        if (cursorCell != null) {
            drawRoundRect(
                color = cursorColor,
                topLeft = Offset(
                    x = cursorCell.x * cellSize + (PADDING_HORIZONTAL + outsidePaddingHorizontal) / 2,
                    y = cursorCell.y * cellSize + (PADDING_VERTICAL + outsidePaddingVertical) / 2
                ),
                size = Size(
                    width = cellSize - PADDING_HORIZONTAL,
                    height = cellSize - PADDING_VERTICAL
                ),
                style = Stroke(
                    width = 3f
                ),
                cornerRadius = CornerRadius(
                    x = cellSize / 8,
                    y = cellSize / 8
                )
            )
        }
    }
}