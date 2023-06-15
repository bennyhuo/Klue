//
// Created by benny on 2022/8/23.
// Copyright (c) 2022 orgName. All rights reserved.
//

import Foundation
import SampleBridge
import SampleBridgeWrapper

extension CommonBridge {
    func registerAllBridges() {
        register(bridge: UtilsImpl().bridge())
        register(bridge: UserApiImpl().bridge())
    }
}

class UserApiImpl: UserApi {
    func getAllUsers() -> [User] {
        UserStore.shared.allUsers
    }

    func getCurrentUser() -> User {
        UserStore.shared.currentUser
    }

    func getUserById(id: Int64) -> User {
        UserStore.shared.allUsers.first {
            $0.id == id
        }!
    }
}

class UserStore {
    static let shared = UserStore()

    var currentUser = User(id: 1000, name: "bennyhuo", age: 10)

    let allUsers = [
        User(id: 0, name: "bennyhuo000", age: 10),
        User(id: 1, name: "bennyhuo001", age: 10),
        User(id: 2, name: "bennyhuo002", age: 10),
        User(id: 3, name: "bennyhuo003", age: 10),
    ]
}
