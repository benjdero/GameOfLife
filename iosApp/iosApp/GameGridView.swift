import Shared
import SwiftUI

struct GameGridView: View {
    private let world: World

    private let onTap: (Int, Int) -> Void

    @State
    private var viewSize: CGSize = .zero

    init(world: World, onTap: @escaping (Int, Int) -> Void) {
        self.world = world
        self.onTap = onTap
    }

    var body: some View {
        ZStack {
            GeometryReader { proxy in
                HStack {}
                    .onAppear {
                        viewSize = proxy.size
                    }
            }
            Canvas { context, size in
                let cellWidth: CGFloat = size.width / CGFloat(world.width)
                let cellHeight: CGFloat = size.height / CGFloat(world.height)
                let cellSize: Int = .init(min(cellWidth, cellHeight))

                let ctxt = context
                world.forEachIndexed { x, y, cell in
                    ctxt.fill(
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
            }.onClickGesture { location in
                let cellWidth: CGFloat = viewSize.width / CGFloat(world.width)
                let cellHeight: CGFloat = viewSize.height / CGFloat(world.height)
                let cellSize: Int = .init(min(cellWidth, cellHeight))
                let x: Int = .init(location.x) / cellSize
                let y: Int = .init(location.y) / cellSize
                onTap(x, y)
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
        ),
        onTap: { _, _ in }
    )
}
