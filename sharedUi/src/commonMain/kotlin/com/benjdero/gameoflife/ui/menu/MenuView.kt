package com.benjdero.gameoflife.ui.menu

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.benjdero.gameoflife.menu.MenuComponent
import com.benjdero.gameoflife.resources.Res
import com.benjdero.gameoflife.resources.app_name
import com.benjdero.gameoflife.resources.menu_start_draw
import com.benjdero.gameoflife.resources.menu_start_game
import com.benjdero.gameoflife.ui.AppIcon
import org.jetbrains.compose.resources.stringResource

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
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppIcon(
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = stringResource(Res.string.app_name)
                )
            }
            Button(
                modifier = Modifier.width(128.dp),
                onClick = component::onStartDraw
            ) {

                Text(
                    text = stringResource(Res.string.menu_start_draw).uppercase()
                )
            }
            Button(
                modifier = Modifier.width(128.dp),
                onClick = component::onStartGame
            ) {
                Text(
                    text = stringResource(Res.string.menu_start_game).uppercase()
                )
            }
            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }
    }
}