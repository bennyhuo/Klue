import SampleBridge

extension Utils {

  public func bridge() -> UtilsBridge {
    UtilsBridge(target: self)
  }

}
