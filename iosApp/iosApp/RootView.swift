import SwiftUI
import shared

struct RootView: View {
    @ObservedObject
    private var childStack: ObservableValue<ChildStack<AnyObject, RootChild>>

    init(component: Root) {
        self.childStack = ObservableValue(component.childStack)
    }

    var body: some View {
        let child = self.childStack.value.active.instance

        switch child {
        case let menu as RootChild.ChildMenu:
            MenuView(
                component: menu.component
            )

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
//        RootView(
//            component: Root()
//        )
        EmptyView()
    }
}
