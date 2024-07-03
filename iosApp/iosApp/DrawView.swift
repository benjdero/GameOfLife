import Shared
import SwiftUI

struct DrawView: View {
    private let component: DrawComponent

    @ObservedObject
    private var observableModel: ObservableValue<DrawComponentModel>

    private var model: DrawComponentModel { observableModel.value }

    init(component: DrawComponent) {
        self.component = component
        observableModel = ObservableValue(component.models)
    }

    var body: some View {
        VStack {
            GameGridView(world: model.world)
            HStack {
                Button(
                    action: component.goBack,
                    label: {
                        Image(systemName: "chevron.backward")
                    }
                )
                Spacer()
                Button(
                    action: component.clearWorld,
                    label: {
                        Image(systemName: "clear")
                    }
                )
                Spacer()
                Button(
                    action: component.randomWorld,
                    label: {
                        Image(systemName: "shuffle")
                        // Image(systemName: "dice")
                    }
                )
                Spacer()
                Group {
                    Button(
                        action: component.load,
                        label: {
                            Image(systemName: "square.and.arrow.down")
                        }
                    )
                    Spacer()
                    Button {
                        component.save(world: model.world)
                    } label: {
                        Image(systemName: "square.and.pencil")
                    }
                }
                Spacer()
                Button(
                    action: component.finish,
                    label: {
                        Image(systemName: "checkmark")
                    }
                )
            }.padding(32)
        }
    }
}

struct DrawView_Previews: PreviewProvider {
    static var previews: some View {
        DrawView(component: DrawComponentMock())
    }
}
