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
import com.benjdero.gameoflife.RootComponent.Child
import com.benjdero.gameoflife.draw.DrawComponent
import com.benjdero.gameoflife.draw.DrawComponentImpl
import com.benjdero.gameoflife.game.GameComponent
import com.benjdero.gameoflife.game.GameComponentImpl
import com.benjdero.gameoflife.load.LoadComponent
import com.benjdero.gameoflife.load.LoadComponentImpl
import com.benjdero.gameoflife.menu.MenuComponent
import com.benjdero.gameoflife.menu.MenuComponentImpl
import com.benjdero.gameoflife.model.World
import com.benjdero.gameoflife.model.dao.DaoService
import com.benjdero.gameoflife.save.SaveComponent
import com.benjdero.gameoflife.save.SaveComponentImpl
import kotlinx.serialization.Serializable

class RootComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    daoService: DaoService
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Menu,
        handleBackButton = true,
        childFactory = { config: Config, childContext: ComponentContext ->
            when (config) {
                Config.Menu -> Child.Menu(
                    component = MenuComponentImpl(
                        componentContext = childContext,
                        storeFactory = storeFactory,
                        output = ::onMenuOutput
                    )
                )
                is Config.Draw -> Child.Draw(
                    component = DrawComponentImpl(
                        componentContext = childContext,
                        storeFactory = storeFactory,
                        world = config.world,
                        output = ::onDrawOutput
                    )
                )
                Config.Load -> Child.Load(
                    component = LoadComponentImpl(
                        componentContext = childContext,
                        storeFactory = storeFactory,
                        daoService = daoService,
                        output = ::onLoadOutput
                    )
                )
                is Config.Save -> Child.Save(
                    component = SaveComponentImpl(
                        componentContext = childContext,
                        storeFactory = storeFactory,
                        daoService = daoService,
                        world = config.world,
                        output = ::onSaveOutput
                    )
                )
                is Config.Game -> Child.Game(
                    component = GameComponentImpl(
                        componentContext = childContext,
                        storeFactory = storeFactory,
                        world = config.world,
                        output = ::onGameOutput
                    )
                )
            }
        }
    )

    private fun onMenuOutput(output: MenuComponent.Output): Unit =
        when (output) {
            MenuComponent.Output.StartDraw -> navigation.push(Config.Draw(null))
            MenuComponent.Output.StartGame -> navigation.push(Config.Game(null))
        }

    private fun onDrawOutput(output: DrawComponent.Output): Unit =
        when (output) {
            is DrawComponent.Output.Finish -> navigation.push(Config.Game(output.world))
            DrawComponent.Output.GoBack -> navigation.pop()
            DrawComponent.Output.Load -> navigation.push(Config.Load)
            is DrawComponent.Output.Save -> navigation.push(Config.Save(output.world))
        }

    private fun onLoadOutput(output: LoadComponent.Output): Unit =
        when (output) {
            LoadComponent.Output.GoBack -> navigation.pop()
            is LoadComponent.Output.WorldSelected -> {
                navigation.pop()
                navigation.replaceCurrent(Config.Draw(output.world))
            }
        }

    private fun onSaveOutput(output: SaveComponent.Output): Unit =
        when (output) {
            SaveComponent.Output.GoBack -> navigation.pop()
        }

    private fun onGameOutput(output: GameComponent.Output): Unit =
        when (output) {
            GameComponent.Output.GoBack -> navigation.pop()
        }

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = stack

    @Serializable
    private sealed class Config {

        @Serializable
        data object Menu : Config()

        @Serializable
        data class Draw(
            val world: World?
        ) : Config()

        @Serializable
        data object Load : Config()

        @Serializable
        data class Save(
            val world: World
        ) : Config()

        @Serializable
        data class Game(
            val world: World?
        ) : Config()
    }
}