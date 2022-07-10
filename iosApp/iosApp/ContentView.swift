import SwiftUI
import shared

struct ContentView: View {
    private let world: World

    @ObservedObject
    private var observableModel: ObservableValue<WorldModel>

    private var model: WorldModel { observableModel.value }

    init(_ world: World) {
        self.world = world
        observableModel = ObservableValue(world.models)
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
                Button(action: world.runGame) {
                    Text(model.running ? "Pause" : "Run")
                }
                Button(action: world.nextStep) {
                    Text("Next")
                }.disabled(model.running)
                Spacer()
            }.padding(16)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(WorldPreview())
    }
}

class WorldPreview: World {
    let models: Value<WorldModel> = Value()

    init() {
        models.setValue(
            WorldModel(
                running: false,
                width: 10,
                height: 15,
                world: KotlinArray(size: 10) { _ in
                    KotlinArray(size: 15) { _ in
                        true
                    }
                }
            ),
            forKey: "WorldModel"
        )
    }

    func nextStep() {}

    func runGame() {}
}
