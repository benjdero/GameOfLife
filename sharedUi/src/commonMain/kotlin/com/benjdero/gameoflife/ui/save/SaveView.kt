package com.benjdero.gameoflife.ui.save

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.benjdero.gameoflife.save.SaveComponent
import com.benjdero.gameoflife.save.SaveComponent.Model
import com.benjdero.gameoflife.ui.common.CellGridView

@Composable
fun SaveView(
    component: SaveComponent
) {
    val model: Model by component.models.subscribeAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = component::exit
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    ) { scaffoldPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CellGridView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                showCursor = false,
                world = model.world
            )
            TextField(
                value = model.name,
                onValueChange = component::setName,
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = component::clearName,
                        enabled = model.canSave
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            )
            Button(
                onClick = component::save,
                enabled = model.canSave
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null
                )
            }
        }
    }

    LaunchedEffect(model.saved) {
        if (model.saved)
            component.exit()
    }
}