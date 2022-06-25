package com.benjdero.gameoflife.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = MyColor.Blue200,
    primaryVariant = MyColor.Blue800,
    secondary = MyColor.Teal200
)

private val LightColorPalette = lightColors(
    primary = MyColor.Blue600,
    primaryVariant = MyColor.Blue800,
    secondary = MyColor.Teal200
)

@Composable
fun MyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}