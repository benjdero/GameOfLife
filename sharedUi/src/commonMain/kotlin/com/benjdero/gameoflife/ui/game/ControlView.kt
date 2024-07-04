package com.benjdero.gameoflife.ui.game

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.benjdero.gameoflife.game.GameComponent
import com.benjdero.gameoflife.model.Speed
import com.benjdero.gameoflife.resources.Res
import com.benjdero.gameoflife.resources.game_next_step
import com.benjdero.gameoflife.resources.game_prev_step
import com.benjdero.gameoflife.resources.game_zoom_in
import com.benjdero.gameoflife.resources.game_zoom_out
import com.benjdero.gameoflife.ui.common.ToggleGridButton
import org.jetbrains.compose.resources.stringResource
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
internal fun RowScope.ControlView(
    model: GameComponent.Model,
    goBack: () -> Unit,
    prevStep: () -> Unit,
    nextStep: () -> Unit,
    canSpeedUp: Boolean,
    canSpeedDown: Boolean,
    speedUp: () -> Unit,
    speedDown: () -> Unit,
    showGrid: Boolean,
    toggleGrid: () -> Unit,
    scale: Float,
    setScale: (Float) -> Unit,
    offset: Offset,
    setOffset: (Offset) -> Unit
) {
    IconButton(
        onClick = goBack
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null
        )
    }
    Spacer(
        modifier = Modifier.width(16.dp)
    )
    IconButton(
        onClick = prevStep,
        enabled = !model.running && model.history.isNotEmpty()
    ) {
        Icon(
            imageVector = Icons.Default.SkipPrevious,
            contentDescription = stringResource(Res.string.game_prev_step)
        )
    }
    IconButton(
        onClick = nextStep,
        enabled = !model.running
    ) {
        Icon(
            imageVector = Icons.Default.SkipNext,
            contentDescription = stringResource(Res.string.game_next_step)
        )
    }
    Spacer(
        modifier = Modifier.width(16.dp)
    )
    Icon(
        modifier = Modifier.rotate(-90f),
        imageVector = Icons.Default.AccountTree,
        contentDescription = null
    )
    Spacer(
        modifier = Modifier.width(4.dp)
    )
    Text(
        text = model.generation.toString()
    )
    Spacer(
        modifier = Modifier.width(4.dp)
    )
    IconButton(
        onClick = speedDown,
        enabled = canSpeedDown
    ) {
        Icon(
            imageVector = Icons.Default.Remove,
            contentDescription = null
        )
    }
    Text(
        text = when (model.speed) {
            Speed.NORMAL -> "x1"
            Speed.FAST_2X -> "x2"
            Speed.FAST_4X -> "x4"
            Speed.FAST_10X -> "x10"
        }
    )
    IconButton(
        onClick = speedUp,
        enabled = canSpeedUp
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
    }
    Spacer(
        modifier = Modifier.weight(1f)
    )
    ToggleGridButton(
        showGrid = showGrid,
        toggleGrid = toggleGrid
    )
    Spacer(
        modifier = Modifier.width(16.dp)
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
            contentDescription = stringResource(Res.string.game_zoom_out)
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
            contentDescription = stringResource(Res.string.game_zoom_in)
        )
    }
}