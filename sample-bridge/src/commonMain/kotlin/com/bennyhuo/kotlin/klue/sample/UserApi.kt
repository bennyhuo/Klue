package com.bennyhuo.kotlin.klue.sample

import com.bennyhuo.klue.annotations.KlueBridge
import kotlinx.serialization.Serializable

/**
 * Created by benny.
 */
@KlueBridge
interface UserApi {

    fun getCurrentUser(): User

    fun getUserById(id: Long): User

    fun getAllUsers(): List<User>

}

@Serializable
data class User(
    val id: Long,
    val name: String,
    val age: Int,
)

