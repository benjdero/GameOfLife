package com.benjdero.gameoflife.ui.draw

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.benjdero.gameoflife.draw.Draw
import com.benjdero.gameoflife.ui.theme.MyTheme
import kotlin.math.min

@Composable
fun DrawView(component: Draw) {
    val model: Draw.Model by component.models.subscribeAsState()

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
                            detectTapGestures { offset: Offset ->
                                val cellWidth: Float = size.width.toFloat() / model.width
                                val cellHeight: Float = size.height.toFloat() / model.height
                                val cellSize: Float = min(cellWidth, cellHeight)
                                val x: Int = (offset.x / cellSize).toInt()
                                val y: Int = (offset.y / cellSize).toInt()
                                component.onDraw(x, y)
                            }
                        }
                ) {
                    CellGridView(model)
                }
            }
        }
    }
}