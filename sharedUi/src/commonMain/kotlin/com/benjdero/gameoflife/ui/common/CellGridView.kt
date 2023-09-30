package com.benjdero.gameoflife.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.benjdero.gameoflife.World
import kotlin.math.min

private const val PADDING_HORIZONTAL = 1f
private const val PADDING_VERTICAL = 1f

@Composable
internal fun CellGridView(
    modifier: Modifier = Modifier,
    world: World
) {
    val aliveCellColor: Color = MaterialTheme.colors.secondary
    val deadCellColor: Color = MaterialTheme.colors.background

    Canvas(
        modifier = modifier
    ) {
        val cellWidth: Float = size.width / world.width
        val cellHeight: Float = size.height / world.height
        val cellSize: Float = min(cellWidth, cellHeight)
        val outsidePaddingHorizontal: Float = size.width - (world.width * cellSize)
        val outsidePaddingVertical: Float = size.height - (world.height * cellSize)

        world.forEachIndexed { x: Int, y: Int, cell: Boolean ->
            drawRect(
                color = if (cell) aliveCellColor else deadCellColor,
                topLeft = Offset(
                    x = x * cellSize + (PADDING_HORIZONTAL + outsidePaddingHorizontal) / 2,
                    y = y * cellSize + (PADDING_VERTICAL + outsidePaddingVertical) / 2
                ),
                size = Size(
                    width = cellSize - PADDING_HORIZONTAL,
                    height = cellSize - PADDING_VERTICAL
                )
            )
        }
    }
}