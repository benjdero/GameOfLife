package com.benjdero.gameoflife.ui.load

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.load.LoadComponentMock
import com.benjdero.gameoflife.ui.theme.MyTheme

@Preview
@Composable
private fun LoadPreview() {
    MyTheme {
        LoadView(
            component = LoadComponentMock()
        )
    }
}
