package com.benjdero.gameoflife.ui.draw

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.game.GameMock
import com.benjdero.gameoflife.ui.game.GameView
import com.benjdero.gameoflife.ui.theme.MyTheme

@Preview
@Composable
fun GamePreview() {
    MyTheme {
        GameView(
            component = GameMock()
        )
    }
}