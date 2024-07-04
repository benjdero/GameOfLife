import Shared
import SwiftUI

struct SaveView: View {
    private let component: SaveComponent

    @ObservedObject
    private var observableModel: ObservableValue<SaveComponentModel>

    private var model: SaveComponentModel { observableModel.value }

    init(component: SaveComponent) {
        self.component = component
        observableModel = ObservableValue(component.models)
    }

    @State private var worldName: String = "World 1"

    var body: some View {
        VStack {
            GameGridView(world: model.world)
            HStack {
                let binding = Binding<String>(
                    get: { model.name },
                    set: { component.setName(name: $0) }
                )
                Button {
                    component.exit()
                } label: {
                    Image(systemName: "chevron.backward")
                }
                TextField(
                    "",
                    text: binding
                )
                .padding(.horizontal, 16.0)
                Button {
                    component.save()
                } label: {
                    Image(systemName: "square.and.pencil")
                }
            }
            .padding(.horizontal, 16.0)
        }
    }
}

#Preview {
    SaveView(
        component: SaveComponentMock()
    )
}
