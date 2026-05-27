package com.benjdero.gameoflife.ui.common

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.onPointerMoveEvent(
    showCursor: Boolean,
    setCursorPosition: (Offset?) -> Unit
): Modifier =
    this
        .onPointerEvent(PointerEventType.Move) { event: PointerEvent ->
            if (showCursor) {
                setCursorPosition(event.changes.last().position)
            }
        }
        .onPointerEvent(PointerEventType.Exit) {
            setCursorPosition(null)
        }
