package com.benjdero.gameoflife.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridOff
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.runtime.Composable

@Composable
fun ToggleGridButton(
    showGrid: Boolean,
    toggleGrid: () -> Unit
) {
    IconButton(
        onClick = toggleGrid,
    ) {
        Icon(
            imageVector = if (showGrid)
                Icons.Default.GridOff
            else
                Icons.Default.GridOn,
            contentDescription = null
        )
    }
}