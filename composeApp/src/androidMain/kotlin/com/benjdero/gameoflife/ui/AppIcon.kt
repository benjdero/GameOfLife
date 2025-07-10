package com.benjdero.gameoflife.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.benjdero.gameoflife.R

@Composable
actual fun AppIcon(
    modifier: Modifier
) {
    Image(
        modifier = modifier,
        imageVector = ImageVector.vectorResource(R.drawable.icon),
        contentDescription = null
    )
}