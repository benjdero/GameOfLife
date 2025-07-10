package com.benjdero.gameoflife.draw

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.theme.MyTheme

@Preview
@Composable
private fun DrawPreview() {
    MyTheme {
        DrawView(
            component = DrawComponentMock()
        )
    }
}