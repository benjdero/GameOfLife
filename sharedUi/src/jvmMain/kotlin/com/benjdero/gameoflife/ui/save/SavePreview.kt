package com.benjdero.gameoflife.ui.save

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.save.SaveComponentMock
import com.benjdero.gameoflife.ui.theme.MyTheme

@Preview
@Composable
private fun SavePreview() {
    MyTheme {
        SaveView(
            component = SaveComponentMock()
        )
    }
}