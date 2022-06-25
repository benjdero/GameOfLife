package com.benjdero.gameoflife

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun RootView() {
    Column {
        WorldView(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        GreetingView(
            Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun WorldView(modifier: Modifier) {
    val worldHeight = 10
    val worldWidth = 15
    val world: Array<Array<Boolean>> = Array(worldHeight) {
        Array(worldWidth) {
            Random.nextBoolean()
        }
    }

    Canvas(
        modifier = modifier
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val cellWidth: Float = canvasWidth / worldWidth
        val cellHeight: Float = canvasHeight / worldHeight

        world.forEachIndexed { r: Int, row: Array<Boolean> ->
            row.forEachIndexed { c: Int, cell: Boolean ->
                drawRect(
                    color = if (cell) Color.Black else Color.White,
                    topLeft = Offset(
                        x = c * cellWidth,
                        y = r * cellHeight
                    ),
                    size = Size(
                        width = cellWidth,
                        height = cellHeight
                    )
                )
            }
        }
    }
}

@Composable
fun GreetingView(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = greet()
    )
}

fun greet(): String {
    return Greeting().greeting()
}