package com.benjdero.gameoflife

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.res.useResource
import org.xml.sax.InputSource
import java.io.InputStream

@Composable
actual fun AppIcon(
    modifier: Modifier
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
        modifier = modifier,
        painter = logo,
        contentDescription = null
    )
}
