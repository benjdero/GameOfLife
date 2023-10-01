import shared
import SwiftUI

@main
struct iOSApp: App {
    @StateObject
    private var rootHolder = RootHolder()

    var body: some Scene {
        WindowGroup {
            RootView(component: rootHolder.component)
                .onAppear { LifecycleRegistryExtKt.resume(self.rootHolder.lifecycle) }
                .onDisappear { LifecycleRegistryExtKt.stop(self.rootHolder.lifecycle) }
        }
    }
}

private class RootHolder: ObservableObject {
    let lifecycle: LifecycleRegistry
    let component: Root

    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()

        component = RootComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle),
            storeFactory: LoggingStoreFactory(delegate: TimeTravelStoreFactory()),
            daoService: DaoService(sqlDriverFactory: SqlDriverFactory())
        )

        lifecycle.onCreate()
    }

    deinit {
        lifecycle.onDestroy()
    }
}
