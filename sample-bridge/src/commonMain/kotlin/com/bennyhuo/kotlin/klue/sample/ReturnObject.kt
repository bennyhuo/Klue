package com.bennyhuo.kotlin.klue.sample

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Created by benny.
 */
@JsExport
@Serializable
class ReturnObject(
    val int: Int,
    val long: Long,
    val float: Float,
    val double: Double,
    val string: String
)