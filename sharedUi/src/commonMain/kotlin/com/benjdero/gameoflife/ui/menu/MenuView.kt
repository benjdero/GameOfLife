package com.benjdero.gameoflife.ui.menu

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import com.benjdero.gameoflife.Res
import com.benjdero.gameoflife.menu.Menu
import com.benjdero.gameoflife.ui.theme.MyTheme
import dev.icerock.moko.resources.compose.stringResource
import org.xml.sax.InputSource
import java.io.InputStream

@Composable
fun MenuView(
    component: Menu
) {
    MyTheme {
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
                    val density = LocalDensity.current
                    val logo = rememberVectorPainter(
                        remember {
                            useResource("icon.xml") { inputStream: InputStream ->
                                loadXmlImageVector(InputSource(inputStream), density)
                            }
                        }
                    )
                    Image(
                        painter = logo,
                        contentDescription = null,
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
}