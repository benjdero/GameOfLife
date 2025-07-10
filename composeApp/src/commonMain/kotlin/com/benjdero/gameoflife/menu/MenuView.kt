package com.benjdero.gameoflife.menu

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benjdero.gameoflife.AppIcon
import com.benjdero.gameoflife.Res
import dev.icerock.moko.resources.compose.fontFamilyResource
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
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppIcon(
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = stringResource(Res.strings.app_name),
                    fontSize = 42.sp,
                    fontFamily = fontFamilyResource(Res.fonts.good_bakwan),
                    color = Color(0xFF444444)
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