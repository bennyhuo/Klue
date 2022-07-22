package com.bennyhuo.klue.common.invoke

import kotlinx.serialization.Serializable

@Serializable
data class KlueFunctionInfo(
    val className: String,
    val functionName: String,
    val parameters: String,
    val callback: String
)

