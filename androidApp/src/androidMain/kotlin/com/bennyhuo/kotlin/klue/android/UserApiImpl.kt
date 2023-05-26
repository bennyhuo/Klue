package com.bennyhuo.kotlin.klue.android

import com.bennyhuo.kotlin.klue.sample.User
import com.bennyhuo.kotlin.klue.sample.UserApi

/**
 * Created by benny.
 */
class UserApiImpl : UserApi {
    override fun getCurrentUser(): User {
        return UserStore.currentUser
    }

    override fun getUserById(id: Long): User {
        return getAllUsers().single { it.id == id }
    }

    override fun getAllUsers(): List<User> {
        return UserStore.allUsers
    }
}

object UserStore {

    var currentUser = User(
        1,
        "bennyhuo",
        10
    )

    val allUsers = listOf(
        User(0, "bennyhuo000", 10),
        User(1, "bennyhuo001", 10),
        User(2, "bennyhuo002", 10),
        User(3, "bennyhuo003", 10),
        User(4, "bennyhuo004", 10),
        User(5, "bennyhuo005", 10),
    )
}