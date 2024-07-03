import Shared
import SwiftUI

struct RootView: View {
    @ObservedObject
    private var childStack: ObservableValue<ChildStack<AnyObject, RootComponentChild>>

    init(component: RootComponent) {
        childStack = ObservableValue(component.childStack)
    }

    var body: some View {
        let child = self.childStack.value.active.instance

        switch child {
        case let menu as RootComponentChild.Menu:
            MenuView(
                component: menu.component
            )
        case let draw as RootComponentChild.Draw:
            DrawView(
                component: draw.component
            )
            .transition(
                .asymmetric(
                    insertion: AnyTransition.move(edge: .trailing),
                    removal: AnyTransition.move(edge: .trailing)
                )
            )
            .animation(.easeInOut)
        case let load as RootComponentChild.Load:
            LoadView(
                component: load.component
            )
            .transition(
                .asymmetric(
                    insertion: AnyTransition.move(edge: .trailing),
                    removal: AnyTransition.move(edge: .trailing)
                )
            )
            .animation(.easeInOut)
        case let save as RootComponentChild.Save:
            SaveView(
                component: save.component
            )
            .transition(
                .asymmetric(
                    insertion: AnyTransition.move(edge: .trailing),
                    removal: AnyTransition.move(edge: .trailing)
                )
            )
            .animation(.easeInOut)
        case let game as RootComponentChild.Game:
            GameView(
                component: game.component
            )
            .transition(
                .asymmetric(
                    insertion: AnyTransition.move(edge: .trailing),
                    removal: AnyTransition.move(edge: .trailing)
                )
            )
            .animation(.easeInOut)

        default: EmptyView()
        }
    }
}
