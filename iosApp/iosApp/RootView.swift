import shared
import SwiftUI

struct RootView: View {
    @ObservedObject
    private var childStack: ObservableValue<ChildStack<AnyObject, RootChild>>

    init(component: Root) {
        childStack = ObservableValue(component.childStack)
    }

    var body: some View {
        let child = self.childStack.value.active.instance

        switch child {
        case let menu as RootChild.ChildMenu:
            MenuView(
                component: menu.component
            )
        case let draw as RootChild.ChildDraw:
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
        case let load as RootChild.ChildLoad:
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
        case let save as RootChild.ChildSave:
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
        case let game as RootChild.ChildGame:
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

struct RootView_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
