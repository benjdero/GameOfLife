import Shared
import SwiftUI

struct GameView: View {
    private let component: GameComponent

    @ObservedObject
    private var observableModel: ObservableValue<GameComponentModel>

    private var model: GameComponentModel { observableModel.value }

    init(component: GameComponent) {
        self.component = component
        observableModel = ObservableValue(component.models)
    }

    var body: some View {
        VStack {
            GameGridView(world: model.world)
            HStack {
                Button(
                    action: component.speedDown,
                    label: {
                        Image(systemName: "minus")
                    }
                )
                .disabled(!model.canSpeedDown)
                let speed: String = switch model.speed {
                case Speed.normal:
                    "x1"
                case Speed.fast2x:
                    "x2"
                case Speed.fast4x:
                    "x4"
                case Speed.fast10x:
                    "x10"
                default:
                    ""
                }
                Text(speed)
                Button(
                    action: component.speedUp,
                    label: {
                        Image(systemName: "plus")
                    }
                )
                .disabled(!model.canSpeedUp)
            }
            HStack {
                Button(
                    action: component.goBack,
                    label: {
                        Image(systemName: "chevron.backward")
                    }
                )
                Spacer()
                Image(systemName: "person.3.fill")
                Text("\(model.generation)")
                Spacer()
                Button(
                    action: component.prevStep,
                    label: {
                        Image(systemName: "backward.fill")
                    }
                ).disabled(model.running || model.history.count == 0)
                Spacer()
                Button(
                    action: component.runGame,
                    label: {
                        Image(
                            systemName: model.running
                                ? "pause.fill"
                                : "play.fill"
                        )
                    }
                )
                Spacer()
                Button(
                    action: component.nextStep,
                    label: {
                        Image(systemName: "forward.fill")
                    }
                ).disabled(model.running)
            }.padding(32)
        }
    }
}

#Preview {
    GameView(
        component: GameComponentMock()
    )
}
