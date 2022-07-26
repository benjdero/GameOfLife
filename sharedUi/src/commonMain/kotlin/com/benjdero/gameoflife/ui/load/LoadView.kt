package com.benjdero.gameoflife.ui.load

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.load.Load
import com.benjdero.gameoflife.load.Load.Model

@Composable
fun LoadView(component: Load) {
    val model: Model by component.models.subscribeAsState()

    LazyColumn {
        items(model.worldList) { world: World ->
            Column(
                modifier =
                Modifier
                    .padding(all = 16.dp)
                    .clickable {
                        component.onWorldSelected(world)
                    }
            ) {
                Text(
                    text = "World"
                )
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
                CellGridView(
                    world = world
                )
            }
        }
    }
}