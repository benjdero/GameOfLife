package com.benjdero.gameoflife

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.game.GameComponent
import com.benjdero.gameoflife.menu.Menu
import com.benjdero.gameoflife.menu.MenuComponent

class RootComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : Root, ComponentContext by componentContext {

    private val router = router<Configuration, Root.Child>(
        initialConfiguration = Configuration.Menu,
        handleBackButton = true,
        childFactory = { configuration: Configuration, componentContext: ComponentContext ->
            when (configuration) {
                Configuration.Game -> Root.Child.ChildGame(
                    component = GameComponent(
                        componentContext = componentContext,
                        storeFactory = storeFactory
                    )
                )
                Configuration.Menu -> Root.Child.ChildMenu(
                    component = MenuComponent(
                        componentContext = componentContext,
                        storeFactory = storeFactory,
                        output = ::onMenuOutput
                    )
                )
            }
        }
    )

    private fun onMenuOutput(output: Menu.Output): Unit =
        when (output) {
            Menu.Output.Start -> router.push(Configuration.Game)
        }

    override val routerState: Value<RouterState<*, Root.Child>> = router.state

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Game : Configuration()

        @Parcelize
        object Menu : Configuration()
    }
}