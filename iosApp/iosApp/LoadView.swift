import Shared
import SwiftUI

struct LoadView: View {
    private let component: LoadComponent

    @ObservedObject
    private var observableModel: ObservableValue<LoadComponentModel>

    private var model: LoadComponentModel { observableModel.value }

    init(component: LoadComponent) {
        self.component = component
        observableModel = ObservableValue(component.models)
    }

    var body: some View {
        VStack {
            Button {
                component.goBack()
            } label: {
                Image(systemName: "chevron.backward")
            }
            LazyVStack {
                ForEach(model.worldList, id: \.self) { item in
                    VStack {
                        HStack {
                            Text("World")
                            Button {
                                component.deleteWorld(world: item)
                            } label: {
                                Image(systemName: "trash")
                            }
                        }
                        GameGridView(world: item)
                    }
                }
            }
        }
    }
}

#Preview {
    LoadView(
        component: LoadComponentMock()
    )
}
