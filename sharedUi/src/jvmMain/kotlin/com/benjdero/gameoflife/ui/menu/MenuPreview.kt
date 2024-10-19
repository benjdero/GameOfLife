package com.benjdero.gameoflife.ui.menu

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.menu.MenuComponentMock
import com.benjdero.gameoflife.ui.theme.MyTheme

@Preview
@Composable
private fun MenuPreview() {
    MyTheme {
        MenuView(
            component = MenuComponentMock()
        )
    }
}
