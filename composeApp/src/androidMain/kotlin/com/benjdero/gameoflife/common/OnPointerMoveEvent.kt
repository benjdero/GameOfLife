package com.benjdero.gameoflife.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset

actual fun Modifier.onPointerMoveEvent(
    showCursor: Boolean,
    setCursorPosition: (Offset?) -> Unit
): Modifier =
    this
