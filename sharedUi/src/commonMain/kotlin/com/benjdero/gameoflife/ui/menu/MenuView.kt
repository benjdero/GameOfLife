package com.benjdero.gameoflife.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.benjdero.gameoflife.menu.Menu
import com.benjdero.gameoflife.ui.theme.MyTheme

@Composable
fun MenuView(component: Menu) {
    MyTheme {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = component::onStart
                ) {
                    Text("Start")
                }
            }
        }
    }
}