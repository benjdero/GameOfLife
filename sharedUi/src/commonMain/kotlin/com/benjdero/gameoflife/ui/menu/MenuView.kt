package com.benjdero.gameoflife.ui.menu

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Speed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.benjdero.gameoflife.Res
import com.benjdero.gameoflife.menu.MenuComponent
import com.benjdero.gameoflife.menu.MenuComponentMock
import com.benjdero.gameoflife.ui.AppIcon
import com.benjdero.gameoflife.ui.common.TestBtnView2
import com.benjdero.gameoflife.ui.theme.MyTheme
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun MenuView(
    component: MenuComponent
) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TestBtnView2(
                icon = Icons.Default.Search,
                text = "100%"
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            TestBtnView2(
                icon = Icons.TwoTone.Speed,
                text = "x4"
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppIcon(
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = stringResource(Res.strings.app_name)
                )
            }
            Button(
                modifier = Modifier.width(128.dp),
                onClick = component::onStartDraw
            ) {
                Text(
                    text = stringResource(Res.strings.menu_start_draw).uppercase()
                )
            }
            Button(
                modifier = Modifier.width(128.dp),
                onClick = component::onStartGame
            ) {
                Text(
                    text = stringResource(Res.strings.menu_start_game).uppercase()
                )
            }
            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }
    }
}

@Preview
@Composable
private fun MenuPreview() {
    MyTheme {
        MenuView(
            component = MenuComponentMock()
        )
    }
}
