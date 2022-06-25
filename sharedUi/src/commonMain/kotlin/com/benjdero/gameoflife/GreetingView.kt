package com.benjdero.gameoflife

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun GreetingView() {
    Text(
        text = greet()
    )
}

fun greet(): String {
    return Greeting().greeting()
}