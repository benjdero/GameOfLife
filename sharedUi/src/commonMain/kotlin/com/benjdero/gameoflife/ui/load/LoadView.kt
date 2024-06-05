package com.benjdero.gameoflife.ui.load

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.benjdero.gameoflife.Res
import com.benjdero.gameoflife.World
import com.benjdero.gameoflife.load.Load
import com.benjdero.gameoflife.load.Load.Model
import com.benjdero.gameoflife.ui.common.CellGridView
import com.benjdero.gameoflife.ui.theme.MyTheme
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun LoadView(
    component: Load
) {
    val model: Model by component.models.subscribeAsState()

    MyTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = component::goBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            }
        ) { scaffoldPadding: PaddingValues ->
            if (model.worldList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = stringResource(Res.strings.load_list_empty)
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.padding(scaffoldPadding)
            ) {
                items(model.worldList) { world: World ->
                    Card(
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .clickable {
                                component.onWorldSelected(world)
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(all = 16.dp)
                        ) {
                            Row {
                                val saved: World.Saved = world.saved
                                Text(
                                    text = if (saved is World.Saved.AsWorld) {
                                        saved.name
                                    } else {
                                        "" // Should never happen here
                                    }
                                )
                                Spacer(
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        component.deleteWorld(world)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            }
                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )
                            CellGridView(
                                modifier = Modifier
                                    .height(240.dp)
                                    .fillMaxWidth(),
                                showCursor = false,
                                world = world
                            )
                        }
                    }
                }
            }
        }
    }
}