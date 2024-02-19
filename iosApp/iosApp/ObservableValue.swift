import Shared
import SwiftUI

public class ObservableValue<T: AnyObject>: ObservableObject {
    @Published
    var value: T

    private var cancellation: Cancellation?

    init(_ value: Value<T>) {
        self.value = value.value
        cancellation = value.observe { [weak self] value in self?.value = value }
    }

    deinit {
        cancellation?.cancel()
    }
}
