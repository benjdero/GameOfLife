package com.benjdero.gameoflife

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.game.GameComponent

class RootComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : Root, ComponentContext by componentContext {

    private val router = router<Configuration, Root.Child>(
        initialConfiguration = Configuration.Game,
        handleBackButton = true,
        childFactory = { configuration: Configuration, componentContext: ComponentContext ->
            when (configuration) {
                Configuration.Game -> Root.Child.ChildGame(
                    component = GameComponent(
                        componentContext = componentContext,
                        storeFactory = storeFactory
                    )
                )
            }
        }
    )

    override val routerState: Value<RouterState<*, Root.Child>> = router.state

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Game : Configuration()
    }
}