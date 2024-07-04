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
            if model.worldList.isEmpty {
                Text(Res.strings().load_list_empty.localized())
            } else {
                ScrollView {
                    LazyVStack {
                        ForEach(model.worldList, id: \.self) { item in
                            VStack {
                                HStack {
                                    if let saved = item.saved as? World.SavedAsWorld {
                                        Text(saved.name)
                                    } else {
                                        Text("") // Should never happen here
                                    }
                                    Spacer()
                                    Button {
                                        component.deleteWorld(world: item)
                                    } label: {
                                        Image(systemName: "trash")
                                    }
                                }
                                .padding(.horizontal, 8.0)
                                GameGridView(
                                    world: item
                                )
                                .frame(maxWidth: .infinity, minHeight: 200.0, maxHeight: 200.0)
                            }
                            .frame(maxWidth: .infinity)
                            .padding(.horizontal, 16.0)
                            .padding(.vertical, 16.0)
                        }
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
