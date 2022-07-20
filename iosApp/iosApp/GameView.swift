import SwiftUI
import shared

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
            LazyVGrid(
                columns: (1...model.width).map { _ in
                    GridItem(.flexible(), spacing: 1)
                },
                spacing: 1
            ) {
                ForEach(model.flatWorld, id: \.id) { item in
                    Rectangle()
                        .fill(item.cell ? .green : .white)
                }
            }
            HStack {
                Button(action: component.runGame) {
                    Text(model.running ? "Pause" : "Run")
                }
                Button(action: component.nextStep) {
                    Text("Next")
                }.disabled(model.running)
                Spacer()
            }.padding(16)
        }
    }
}

struct GameView_Previews: PreviewProvider {
    static var previews: some View {
        GameView(
            component: GamePreview()
        )
    }
}

class GamePreview: Game {
    let models: Value<GameModel> = valueOf(
        GameModel(
            running: false,
            width: 10,
            height: 15,
            world: KotlinArray(size: 10) { _ in
                KotlinArray(size: 15) { _ in
                    true
                }
            }
        )
    )

    func nextStep() {}

    func runGame() {}
}
