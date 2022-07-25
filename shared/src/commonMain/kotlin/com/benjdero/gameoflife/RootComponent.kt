package com.benjdero.gameoflife

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.draw.Draw
import com.benjdero.gameoflife.draw.DrawComponent
import com.benjdero.gameoflife.game.Game
import com.benjdero.gameoflife.game.GameComponent
import com.benjdero.gameoflife.menu.Menu
import com.benjdero.gameoflife.menu.MenuComponent

class RootComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : Root, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Menu,
        handleBackButton = true,
        childFactory = { configuration: Configuration, componentContext: ComponentContext ->
            when (configuration) {
                Configuration.Menu -> Root.Child.ChildMenu(
                    component = MenuComponent(
                        componentContext = componentContext,
                        storeFactory = storeFactory,
                        output = ::onMenuOutput
                    )
                )
                Configuration.Draw -> Root.Child.ChildDraw(
                    component = DrawComponent(
                        componentContext = componentContext,
                        storeFactory = storeFactory,
                        output = ::onDrawOutput
                    )
                )
                is Configuration.Game -> Root.Child.ChildGame(
                    component = GameComponent(
                        componentContext = componentContext,
                        storeFactory = storeFactory,
                        world = configuration.world,
                        output = ::onGameOutput
                    )
                )
            }
        }
    )

    private fun onMenuOutput(output: Menu.Output): Unit =
        when (output) {
            Menu.Output.StartDraw -> navigation.push(Configuration.Draw)
            Menu.Output.StartGame -> navigation.push(Configuration.Game(World.random(15, 10)))
        }

    private fun onDrawOutput(output: Draw.Output): Unit =
        when (output) {
            is Draw.Output.Finish -> navigation.push(Configuration.Game(output.world))
            Draw.Output.GoBack -> navigation.pop()
        }

    private fun onGameOutput(output: Game.Output): Unit =
        when (output) {
            Game.Output.GoBack -> navigation.pop()
        }

    override val childStack: Value<ChildStack<*, Root.Child>> = stack

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Menu : Configuration()

        @Parcelize
        object Draw : Configuration()

        @Parcelize
        data class Game(val world: World) : Configuration()
    }
}