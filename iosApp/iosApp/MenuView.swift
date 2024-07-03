import Shared
import SwiftUI

struct MenuView: View {
    private let component: MenuComponent

    @ObservedObject
    private var observableModel: ObservableValue<MenuComponentModel>

    private var model: MenuComponentModel { observableModel.value }

    init(component: MenuComponent) {
        self.component = component
        observableModel = ObservableValue(component.models)
    }

    var body: some View {
        VStack {
            Button(action: component.onStartDraw) {
                Text(Res.strings().menu_start_draw.localized())
            }
            Button(action: component.onStartGame) {
                Text(Res.strings().menu_start_game.localized())
            }
        }
    }
}

struct MenuView_Previews: PreviewProvider {
    static var previews: some View {
        MenuView(
            component: MenuPreview()
        )
    }
}

class MenuPreview: MenuComponent {
    let models: Value<MenuComponentModel> = valueOf(
        MenuComponentModel(
            unused: 0
        )
    )

    func onStartDraw() {}

    func onStartGame() {}
}
