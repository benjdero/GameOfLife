import Shared
import SwiftUI

struct GameView: View {
    private let component: Game

    @ObservedObject
    private var observableModel: ObservableValue<GameModel>

    private var model: GameModel { observableModel.value }

    init(component: Game) {
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
                Image(systemName: "person.3.fill")
                Text("\(model.generation)")
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
                    action: component.prevStep,
                    label: {
                        Image(systemName: "backward.fill")
                    }
                ).disabled(model.running || model.history.count == 0)
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

struct GameView_Previews: PreviewProvider {
    static var previews: some View {
        GameView(
            component: GameMock()
        )
    }
}
