import SampleBridge

extension UserApi {

  public func bridge() -> UserApiBridge {
    UserApiBridge(target: self)
  }

}
