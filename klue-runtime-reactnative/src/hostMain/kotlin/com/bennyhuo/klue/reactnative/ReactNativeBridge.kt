package com.bennyhuo.klue.reactnative

import com.bennyhuo.klue.common.CommonBridge
import com.bennyhuo.klue.common.invoke.KlueFunctionInfo
import com.bennyhuo.klue.common.invoke.KlueFunctionResult
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Created by benny at 2022/7/19 23:19.
 */
object ReactNativeBridge: CommonBridge() {

    fun call(value: String): KlueFunctionResult {
        val functionInfo = try {
            Json.decodeFromString<KlueFunctionInfo>(value)
        } catch (e: Exception) {
            e.printStackTrace()
            return KlueFunctionResult(-1, e.toString())
        }

        return call(functionInfo)
    }
}