package com.benjdero.gameoflife.ui.game

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.benjdero.gameoflife.Res
import com.benjdero.gameoflife.game.Game
import dev.icerock.moko.resources.compose.stringResource
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
internal fun RowScope.ControlView(
    model: Game.Model,
    prevStep: () -> Unit,
    nextStep: () -> Unit,
    scale: Float,
    setScale: (Float) -> Unit,
    offset: Offset,
    setOffset: (Offset) -> Unit
) {
    IconButton(
        onClick = prevStep,
        enabled = !model.running && model.history.isNotEmpty()
    ) {
        Icon(
            imageVector = Icons.Default.SkipPrevious,
            contentDescription = stringResource(Res.strings.game_prev_step)
        )
    }
    IconButton(
        onClick = nextStep,
        enabled = !model.running
    ) {
        Icon(
            imageVector = Icons.Default.SkipNext,
            contentDescription = stringResource(Res.strings.game_next_step)
        )
    }
    Spacer(
        modifier = Modifier.weight(1f)
    )
    IconButton(
        onClick = {
            setScale(max(scale - 0.5f, 1f))
            setOffset(offset)
        },
        enabled = scale > 1f
    ) {
        Icon(
            imageVector = Icons.Default.ZoomOut,
            contentDescription = stringResource(Res.strings.game_zoom_out)
        )
    }
    Text(
        text = "${(scale * 100).roundToInt()}%"
    )
    IconButton(
        onClick = {
            setScale(scale + 0.5f)
        }
    ) {
        Icon(
            imageVector = Icons.Default.ZoomIn,
            contentDescription = stringResource(Res.strings.game_zoom_in)
        )
    }
}