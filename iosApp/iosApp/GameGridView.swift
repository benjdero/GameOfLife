import shared
import SwiftUI

struct GameGridView: View {
    private let world: World

    init(world: World) {
        self.world = world
    }

    var body: some View {
        if #available(iOS 15.0, *) {
            Canvas { context, size in
                let cellWidth: CGFloat = size.width / CGFloat(world.width)
                let cellHeight: CGFloat = size.height / CGFloat(world.height)
                let cellSize: Int = .init(min(cellWidth, cellHeight))

                let c = context
                world.forEachIndexed { x, y, cell in
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
                columns: (1 ... world.width).map { _ in
                    GridItem(.flexible(), spacing: 1)
                },
                spacing: 1
            ) {
                ForEach(world.toFlatWorld(), id: \.id) { item in
                    Rectangle()
                        .fill(item.cell ? .green : .white)
                }
            }
        }
    }
}

struct GameGridView_Previews: PreviewProvider {
    static var previews: some View {
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
}
