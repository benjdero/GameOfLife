package com.benjdero.gameoflife.menu

import com.arkivanov.decompose.value.Value

interface Menu {
    val models: Value<Model>

    fun onStartDraw()

    fun onStartGame()

    data class Model(
        val unused: Int
    )

    sealed class Output {
        data object StartDraw : Output()
        data object StartGame : Output()
    }
}