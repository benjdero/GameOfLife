package com.benjdero.gameoflife.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.benjdero.gameoflife.Res
import com.benjdero.gameoflife.inter_black
import com.benjdero.gameoflife.inter_blackitalic
import com.benjdero.gameoflife.inter_bold
import com.benjdero.gameoflife.inter_bolditalic
import com.benjdero.gameoflife.inter_extrabold
import com.benjdero.gameoflife.inter_extrabolditalic
import com.benjdero.gameoflife.inter_extralight
import com.benjdero.gameoflife.inter_extralightitalic
import com.benjdero.gameoflife.inter_italic
import com.benjdero.gameoflife.inter_light
import com.benjdero.gameoflife.inter_lightitalic
import com.benjdero.gameoflife.inter_medium
import com.benjdero.gameoflife.inter_mediumitalic
import com.benjdero.gameoflife.inter_regular
import com.benjdero.gameoflife.inter_semibold
import com.benjdero.gameoflife.inter_semibolditalic
import com.benjdero.gameoflife.inter_thin
import com.benjdero.gameoflife.inter_thinitalic
import dev.icerock.moko.resources.compose.asFont

@Composable
fun getDefaultFont(): FontFamily =
    FontFamily(
        Res.fonts.inter_thin.asFont(
            FontWeight.Thin
        )!!,
        Res.fonts.inter_thinitalic.asFont(
            weight = FontWeight.Thin,
            style = FontStyle.Italic
        )!!,
        Res.fonts.inter_extralight.asFont(
            weight = FontWeight.ExtraLight
        )!!,
        Res.fonts.inter_extralightitalic.asFont(
            weight = FontWeight.ExtraLight,
            style = FontStyle.Italic
        )!!,
        Res.fonts.inter_light.asFont(
            weight = FontWeight.Light
        )!!,
        Res.fonts.inter_lightitalic.asFont(
            weight = FontWeight.Light,
            style = FontStyle.Italic
        )!!,
        Res.fonts.inter_regular.asFont(
            weight = FontWeight.Normal
        )!!,
        Res.fonts.inter_italic.asFont(
            weight = FontWeight.Normal,
            style = FontStyle.Italic
        )!!,
        Res.fonts.inter_medium.asFont(
            weight = FontWeight.Medium
        )!!,
        Res.fonts.inter_mediumitalic.asFont(
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        )!!,
        Res.fonts.inter_semibold.asFont(
            weight = FontWeight.SemiBold
        )!!,
        Res.fonts.inter_semibolditalic.asFont(
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic
        )!!,
        Res.fonts.inter_bold.asFont(
            weight = FontWeight.Bold
        )!!,
        Res.fonts.inter_bolditalic.asFont(
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        )!!,
        Res.fonts.inter_extrabold.asFont(
            weight = FontWeight.Bold
        )!!,
        Res.fonts.inter_extrabolditalic.asFont(
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        )!!,
        Res.fonts.inter_black.asFont(
            weight = FontWeight.Bold
        )!!,
        Res.fonts.inter_blackitalic.asFont(
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        )!!
    )

@Composable
fun MyTypography(): Typography =
    Typography(
        defaultFontFamily = getDefaultFont(),
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
