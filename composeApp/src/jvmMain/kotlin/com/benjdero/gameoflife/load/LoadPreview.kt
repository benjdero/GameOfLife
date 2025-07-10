package com.benjdero.gameoflife.load

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.theme.MyTheme

@Preview
@Composable
private fun LoadPreview() {
    MyTheme {
        LoadView(
            component = LoadComponentMock()
        )
    }
}
