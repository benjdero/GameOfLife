import Shared
import SwiftUI

struct GameGridView: View {
    private let world: World

    init(world: World) {
        self.world = world
    }

    var body: some View {
        Canvas { context, size in
            let cellWidth: CGFloat = size.width / CGFloat(world.width)
            let cellHeight: CGFloat = size.height / CGFloat(world.height)
            let cellSize: Int = .init(min(cellWidth, cellHeight))

            let c = context
            world.forEachIndexed { x, y, cell in
                c.fill(
                    Path(
                        CGRect(
                            x: Int(truncating: x) * cellSize,
                            y: Int(truncating: y) * cellSize,
                            width: cellSize,
                            height: cellSize
                        )
                    ),
                    with: .color(Bool(truncating: cell) ? .green : .white)
                )
            }
        }
    }
}

#Preview {
    GameGridView(
        world: World(
            saved: World.SavedNot(),
            width: 15,
            height: 8,
            cells: KotlinBooleanArray(
                size: 120,
                init: { (index: KotlinInt) -> (KotlinBoolean) in
                    KotlinBoolean(value: Int(index) % 2 == 0)
                }
            )
        )
    )
}
