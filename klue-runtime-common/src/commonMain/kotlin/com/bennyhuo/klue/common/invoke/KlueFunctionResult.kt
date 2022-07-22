package com.bennyhuo.klue.common.invoke

import kotlinx.serialization.Serializable

@Serializable
data class KlueFunctionResult(
    val code: Int,
    val data: String
)