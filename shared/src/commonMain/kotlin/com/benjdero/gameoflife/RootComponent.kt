package com.benjdero.gameoflife

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.benjdero.gameoflife.draw.Draw
import com.benjdero.gameoflife.draw.DrawComponent
import com.benjdero.gameoflife.game.Game
import com.benjdero.gameoflife.game.GameComponent
import com.benjdero.gameoflife.load.Load
import com.benjdero.gameoflife.load.LoadComponent
import com.benjdero.gameoflife.menu.Menu
import com.benjdero.gameoflife.menu.MenuComponent
import com.benjdero.gameoflife.model.dao.DaoService

class RootComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    daoService: DaoService
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
                is Configuration.Draw -> Root.Child.ChildDraw(
                    component = DrawComponent(
                        componentContext = componentContext,
                        storeFactory = storeFactory,
                        daoService = daoService,
                        world = configuration.world,
                        output = ::onDrawOutput
                    )
                )
                Configuration.Load -> Root.Child.ChildLoad(
                    component = LoadComponent(
                        componentContext = componentContext,
                        storeFactory = storeFactory,
                        daoService = daoService,
                        output = ::onLoadOutput
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
            Menu.Output.StartDraw -> navigation.push(Configuration.Draw(null))
            Menu.Output.StartGame -> navigation.push(Configuration.Game(World.random(15, 10)))
        }

    private fun onDrawOutput(output: Draw.Output): Unit =
        when (output) {
            is Draw.Output.Finish -> navigation.push(Configuration.Game(output.world))
            Draw.Output.GoBack -> navigation.pop()
            Draw.Output.Load -> navigation.replaceCurrent(Configuration.Load)
        }

    private fun onLoadOutput(output: Load.Output): Unit =
        when (output) {
            is Load.Output.WorldSelected -> navigation.replaceCurrent(Configuration.Draw(output.world))
        }

    private fun onGameOutput(output: Game.Output): Unit =
        when (output) {
            Game.Output.GoBack -> navigation.pop()
        }

    override val childStack: Value<ChildStack<*, Root.Child>> = stack

    private sealed class Configuration : Parcelable {
        @Parcelize
        data object Menu : Configuration()

        @Parcelize
        data class Draw(val world: World?) : Configuration()

        @Parcelize
        data object Load : Configuration()

        @Parcelize
        data class Game(val world: World) : Configuration()
    }
}