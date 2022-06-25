package com.benjdero.gameoflife.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.benjdero.gameoflife.Greeting

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GreetingView()
        }
    }
}

@Composable
fun GreetingView() {
    Text(
        text = greet()
    )
}

fun greet(): String {
    return Greeting().greeting()
}
