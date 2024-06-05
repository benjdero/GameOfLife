import Shared
import SwiftUI

struct LoadView: View {
    private let component: Load

    @ObservedObject
    private var observableModel: ObservableValue<LoadModel>

    private var model: LoadModel { observableModel.value }

    init(component: Load) {
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

struct LoadView_Previews: PreviewProvider {
    static var previews: some View {
        LoadView(component: LoadMock())
    }
}
