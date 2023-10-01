package com.benjdero.gameoflife.ui.draw

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.load.LoadMock
import com.benjdero.gameoflife.ui.load.LoadView
import com.benjdero.gameoflife.ui.theme.MyTheme

@Preview
@Composable
fun LoadPreview() {
    MyTheme {
        LoadView(
            component = LoadMock()
        )
    }
}
