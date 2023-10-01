package com.benjdero.gameoflife.ui.draw

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.menu.MenuMock
import com.benjdero.gameoflife.ui.menu.MenuView
import com.benjdero.gameoflife.ui.theme.MyTheme

@Preview
@Composable
fun MenuPreview() {
    MyTheme {
        MenuView(
            component = MenuMock()
        )
    }
}
