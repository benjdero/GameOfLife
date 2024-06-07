package com.benjdero.gameoflife.load

import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.model.World

interface LoadComponent {
    val models: Value<Model>

    fun onWorldSelected(world: World)

    fun deleteWorld(world: World)

    fun goBack()

    data class Model(
        val worldList: List<World>
    )

    sealed class Output {
        data object GoBack : Output()
        data class WorldSelected(val world: World) : Output()
    }
}