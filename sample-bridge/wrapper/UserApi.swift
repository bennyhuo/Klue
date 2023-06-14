import SampleBridge

extension UserApi {

  func bridge() -> UserApiBridge {
    UserApiBridge(target: self)
  }

}
