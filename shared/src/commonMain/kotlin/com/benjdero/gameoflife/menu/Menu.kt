package com.benjdero.gameoflife.menu

import com.arkivanov.decompose.value.Value

interface Menu {
    val models: Value<Model>

    fun onStart()

    data class Model(
        val unused: Int
    )

    sealed class Output {
        object Start : Output()
    }
}