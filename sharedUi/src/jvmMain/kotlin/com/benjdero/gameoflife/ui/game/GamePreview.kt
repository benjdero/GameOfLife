package com.benjdero.gameoflife.ui.game

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.game.GameComponentMock
import com.benjdero.gameoflife.ui.theme.MyTheme

@Preview
@Composable
private fun GamePreview() {
    MyTheme {
        GameView(
            component = GameComponentMock()
        )
    }
}