package com.benjdero.gameoflife.ui.draw

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.draw.DrawComponentMock
import com.benjdero.gameoflife.ui.theme.MyTheme

@Preview
@Composable
private fun DrawPreview() {
    MyTheme {
        DrawView(
            component = DrawComponentMock()
        )
    }
}