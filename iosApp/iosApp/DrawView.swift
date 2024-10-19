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
            ZStack {
                GameGridView(
                    world: model.world,
                    onTap: { x, y in
                        component.onDraw(x: Int32(x), y: Int32(y))
                    }
                )
                HStack {
                    Button(
                        action: component.increaseLeft,
                        label: {
                            Image(systemName: "arrow.left.circle")
                        }
                    )
                    Button(
                        action: component.decreaseLeft,
                        label: {
                            Image(systemName: "arrow.right.circle")
                        }
                    )
                    .disabled(!model.allowDecreaseWidth)
                    Spacer()
                }
                VStack {
                    Button(
                        action: component.increaseTop,
                        label: {
                            Image(systemName: "arrow.up.circle")
                        }
                    )
                    Button(
                        action: component.decreaseTop,
                        label: {
                            Image(systemName: "arrow.down.circle")
                        }
                    )
                    .disabled(!model.allowDecreaseHeight)
                    Spacer()
                }
                HStack {
                    Spacer()
                    Button(
                        action: component.decreaseRight,
                        label: {
                            Image(systemName: "arrow.left.circle")
                        }
                    )
                    .disabled(!model.allowDecreaseWidth)
                    Button(
                        action: component.increaseRight,
                        label: {
                            Image(systemName: "arrow.right.circle")
                        }
                    )
                }
                VStack {
                    Spacer()
                    Button(
                        action: component.decreaseBottom,
                        label: {
                            Image(systemName: "arrow.up.circle")
                        }
                    )
                    .disabled(!model.allowDecreaseHeight)
                    Button(
                        action: component.increaseBottom,
                        label: {
                            Image(systemName: "arrow.down.circle")
                        }
                    )
                }
            }
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

#Preview {
    DrawView(
        component: DrawComponentMock()
    )
}
