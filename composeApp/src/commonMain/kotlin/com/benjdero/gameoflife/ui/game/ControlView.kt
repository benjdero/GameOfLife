package com.benjdero.gameoflife.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.benjdero.gameoflife.Res
import com.benjdero.gameoflife.game.GameComponent
import com.benjdero.gameoflife.model.Speed
import com.benjdero.gameoflife.ui.common.ToggleGridButton
import dev.icerock.moko.resources.compose.stringResource
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ControlView(
    model: GameComponent.Model,
    goBack: () -> Unit,
    prevStep: () -> Unit,
    runGame: () -> Unit,
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
    FlowRow(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = goBack
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = null
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
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
                onClick = runGame
            ) {
                Icon(
                    imageVector = if (model.running)
                        Icons.Default.Pause
                    else
                        Icons.Default.PlayArrow,
                    contentDescription = if (model.running)
                        stringResource(Res.strings.game_pause)
                    else
                        stringResource(Res.strings.game_run),
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
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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
        }
        ToggleGridButton(
            showGrid = showGrid,
            toggleGrid = toggleGrid
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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
    }
}
