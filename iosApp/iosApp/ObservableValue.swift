import Shared
import SwiftUI

public class ObservableValue<T: AnyObject>: ObservableObject {
    @Published
    var value: T

    private var cancellation: Cancellation?

    init(_ val: Value<T>) {
        value = val.value
        cancellation = val.subscribe { [weak self] value in self?.value = value }
    }

    deinit {
        cancellation?.cancel()
    }
}
