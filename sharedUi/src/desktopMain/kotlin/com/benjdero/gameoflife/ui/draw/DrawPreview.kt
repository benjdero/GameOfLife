package com.benjdero.gameoflife.ui.draw

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.draw.Draw
import com.benjdero.gameoflife.draw.Draw.Model
import com.benjdero.gameoflife.ui.theme.MyTheme

@Preview
@Composable
fun DrawPreview() {
    MyTheme {
        DrawView(
            component = object : Draw {
                override val models: Value<Model> = MutableValue(
                    initialValue = Model(
                        world = World(
                            saved = World.Saved.Not,
                            width = 4,
                            height = 3,
                            cells = booleanArrayOf(
                                true, true, false, true,
                                false, true, false, false,
                                true, false, true, false
                            )
                        ),
                        showGrid = false,
                        allowDecreaseWidth = true,
                        allowDecreaseHeight = true
                    )
                )

                override fun onDraw(x: Int, y: Int) {}
                override fun onDrawValue(x: Int, y: Int, cell: Boolean) {}
                override fun clearWorld() {}
                override fun randomWorld() {}
                override fun toggleGrid() {}
                override fun increaseLeft() {}
                override fun decreaseLeft() {}
                override fun increaseTop() {}
                override fun decreaseTop() {}
                override fun increaseRight() {}
                override fun decreaseRight() {}
                override fun increaseBottom() {}
                override fun decreaseBottom() {}
                override fun load() {}
                override fun save() {}
                override fun finish() {}
                override fun goBack() {}
            }
        )
    }
}