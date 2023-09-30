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
            if #available(iOS 15.0, *) {
                Canvas { context, size in
                    let cellWidth: CGFloat = size.width / CGFloat(model.world.width)
                    let cellHeight: CGFloat = size.height / CGFloat(model.world.height)
                    let cellSize: Int = Int(min(cellWidth, cellHeight))

                    let c = context
                    model.world.forEachIndexed { x, y, cell in
                        c.fill(
                            Path(
                                CGRect(
                                    x: Int(x) * cellSize,
                                    y: Int(y) * cellSize,
                                    width: cellSize,
                                    height: cellSize
                                )
                            ),
                            with: .color(Bool(cell) ? .green : .white)
                        )
                    }
                }
            } else {
                LazyVGrid(
                    columns: (1...model.world.width).map { _ in
                        GridItem(.flexible(), spacing: 1)
                    },
                    spacing: 1
                ) {
                    ForEach(model.flatWorld, id: \.id) { item in
                        Rectangle()
                            .fill(item.cell ? .green : .white)
                    }
                }
            }
            HStack {
                Button(action: component.runGame) {
                    Text(
                        model.running
                            ? Res.strings().game_pause.localized()
                            : Res.strings().game_run.localized()
                    )
                }
                Button(action: component.prevStep) {
                    Text(Res.strings().game_prev_step.localized())
                }.disabled(model.running || model.history.count == 0)
                Button(action: component.nextStep) {
                    Text(Res.strings().game_next_step.localized())
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
            generation: 0,
            world: World(
                saved: World.SavedNot(),
                width: 10,
                height: 15,
                cells: KotlinBooleanArray(size: 150) { index in
                    true
                }
            ),
            showGrid: false,
            history: []
        )
    )

    func runGame() {}

    func prevStep() {}

    func nextStep() {}

    func toggleGrid() {}

    func goBack() {}
}
