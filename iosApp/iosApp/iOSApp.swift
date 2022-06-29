import SwiftUI
import shared

@main
struct iOSApp: App {
    @StateObject
    private var rootHolder = RootHolder()

    var body: some Scene {
        WindowGroup {
            ContentView(rootHolder.root)
                .onAppear { LifecycleRegistryExtKt.resume(self.rootHolder.lifecycle) }
                .onDisappear { LifecycleRegistryExtKt.stop(self.rootHolder.lifecycle) }
        }
    }
}

private class RootHolder: ObservableObject {
    let lifecycle: LifecycleRegistry
    let root: World

    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()

        root = WorldComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle),
            storeFactory: LoggingStoreFactory(delegate: TimeTravelStoreFactory())
        )

        lifecycle.onCreate()
    }

    deinit {
        lifecycle.onDestroy()
    }
}
