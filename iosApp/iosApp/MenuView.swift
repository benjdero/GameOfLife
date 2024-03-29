import shared
import SwiftUI

struct MenuView: View {
    private let component: shared.Menu

    @ObservedObject
    private var observableModel: ObservableValue<MenuModel>

    private var model: MenuModel { observableModel.value }

    init(component: shared.Menu) {
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

class MenuPreview: shared.Menu {
    let models: Value<MenuModel> = valueOf(
        MenuModel(
            unused: 0
        )
    )

    func onStartDraw() {}

    func onStartGame() {}
}
