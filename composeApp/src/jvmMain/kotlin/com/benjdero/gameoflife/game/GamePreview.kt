package com.benjdero.gameoflife.game

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.theme.MyTheme

@Preview
@Composable
private fun GamePreview() {
    MyTheme {
        GameView(
            component = GameComponentMock()
        )
    }
}