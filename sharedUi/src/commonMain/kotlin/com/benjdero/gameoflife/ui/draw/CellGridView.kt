package com.benjdero.gameoflife.ui.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.benjdero.gameoflife.draw.Draw.Model
import kotlin.math.min

private const val PADDING_HORIZONTAL = 1f
private const val PADDING_VERTICAL = 1f

@Composable
internal fun CellGridView(model: Model) {
    val cellColor: Color = MaterialTheme.colors.secondary

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val cellWidth: Float = size.width / model.world.width
        val cellHeight: Float = size.height / model.world.height
        val cellSize: Float = min(cellWidth, cellHeight)

        model.world.forEachIndexed { x: Int, y: Int, cell: Boolean ->
            drawRect(
                color = if (cell) cellColor else Color.White,
                topLeft = Offset(
                    x = x * cellSize + PADDING_HORIZONTAL / 2,
                    y = y * cellSize + PADDING_VERTICAL / 2
                ),
                size = Size(
                    width = cellSize - PADDING_HORIZONTAL,
                    height = cellSize - PADDING_VERTICAL
                )
            )
        }
    }
}