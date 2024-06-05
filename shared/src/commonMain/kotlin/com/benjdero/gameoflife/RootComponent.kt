package com.benjdero.gameoflife

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
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
import com.benjdero.gameoflife.save.Save
import com.benjdero.gameoflife.save.SaveComponent
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    daoService: DaoService
) : Root, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
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
                is Configuration.Save -> Root.Child.ChildSave(
                    component = SaveComponent(
                        componentContext = componentContext,
                        storeFactory = storeFactory,
                        daoService = daoService,
                        world = configuration.world,
                        output = ::onSaveOutput
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
            Menu.Output.StartGame -> navigation.push(Configuration.Game(null))
        }

    private fun onDrawOutput(output: Draw.Output): Unit =
        when (output) {
            is Draw.Output.Finish -> navigation.push(Configuration.Game(output.world))
            Draw.Output.GoBack -> navigation.pop()
            Draw.Output.Load -> navigation.push(Configuration.Load)
            is Draw.Output.Save -> navigation.push(Configuration.Save(output.world))
        }

    private fun onLoadOutput(output: Load.Output): Unit =
        when (output) {
            Load.Output.GoBack -> navigation.pop()
            is Load.Output.WorldSelected -> {
                navigation.pop()
                navigation.replaceCurrent(Configuration.Draw(output.world))
            }
        }

    private fun onSaveOutput(output: Save.Output): Unit =
        when (output) {
            Save.Output.GoBack -> navigation.pop()
        }

    private fun onGameOutput(output: Game.Output): Unit =
        when (output) {
            Game.Output.GoBack -> navigation.pop()
        }

    override val childStack: Value<ChildStack<*, Root.Child>> = stack

    @Serializable
    private sealed class Configuration {

        @Serializable
        data object Menu : Configuration()

        @Serializable
        data class Draw(val world: World?) : Configuration()

        @Serializable
        data object Load : Configuration()

        @Serializable
        data class Save(val world: World) : Configuration()

        @Serializable
        data class Game(val world: World?) : Configuration()
    }
}