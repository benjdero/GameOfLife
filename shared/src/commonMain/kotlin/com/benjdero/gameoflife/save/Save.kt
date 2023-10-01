package com.benjdero.gameoflife.save

import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.World

interface Save {
    val models: Value<Model>

    fun setName(name: String)

    fun clearName()

    fun save()

    fun exit()

    data class Model(
        val name: String,
        val world: World,
        val canSave: Boolean,
        val saved: Boolean
    )

    sealed class Output {
        data object GoBack : Output()
    }
}