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

    var body: some View {
        VStack {
            Button {
                component.exit()
            } label: {
                Image(systemName: "chevron.backward")
            }
            GameGridView(world: model.world)
            Button {
                component.save()
            } label: {
                Image(systemName: "square.and.pencil")
            }
        }
    }
}

struct SaveView_Previews: PreviewProvider {
    static var previews: some View {
        SaveView(component: SaveComponentMock())
    }
}
