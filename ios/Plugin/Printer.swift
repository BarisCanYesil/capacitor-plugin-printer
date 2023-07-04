import Foundation

@objc public class Printer: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
