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
            Spacer()
            AppIconView()
            Text(Res.strings().app_name.localized())
                .font(.title)
            Spacer()
            Button(action: component.onStartDraw) {
                Text(Res.strings().menu_start_draw.localized())
                    .frame(maxWidth: .infinity)
                    .defaultButtonPadding()
            }
            .buttonStyle(.bordered)
            Button(action: component.onStartGame) {
                Text(Res.strings().menu_start_game.localized())
                    .frame(maxWidth: .infinity)
                    .defaultButtonPadding()
            }
            .buttonStyle(.borderedProminent)
        }.padding(
            EdgeInsets(
                top: 32.0,
                leading: 16.0,
                bottom: 32.0,
                trailing: 16.0
            )
        )
    }
}

extension View {
    func defaultButtonPadding() -> some View {
        padding(
            EdgeInsets(
                top: 8.0,
                leading: 0.0,
                bottom: 8.0,
                trailing: 0.0
            )
        )
    }
}

#Preview {
    MenuView(
        component: MenuComponentMock()
    )
}
