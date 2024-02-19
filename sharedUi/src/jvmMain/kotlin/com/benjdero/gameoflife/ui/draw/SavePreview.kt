package com.benjdero.gameoflife.ui.draw

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.save.SaveMock
import com.benjdero.gameoflife.ui.save.SaveView
import com.benjdero.gameoflife.ui.theme.MyTheme

@Preview
@Composable
fun SavePreview() {
    MyTheme {
        SaveView(
            component = SaveMock()
        )
    }
}