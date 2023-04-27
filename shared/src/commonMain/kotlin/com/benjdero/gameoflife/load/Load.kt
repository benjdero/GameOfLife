package com.benjdero.gameoflife.load

import com.arkivanov.decompose.value.Value
import com.benjdero.gameoflife.World

interface Load {
    val models: Value<Model>

    fun onWorldSelected(world: World)

    fun deleteWorld(world: World)

    data class Model(
        val worldList: List<World>
    )

    sealed class Output {
        data class WorldSelected(val world: World) : Output()
    }
}