package com.bennyhuo.klue.webview

import com.bennyhuo.klue.common.CommonBridge
import com.bennyhuo.klue.common.invoke.KlueFunctionInfo
import com.bennyhuo.klue.common.invoke.KlueFunctionResult
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Created by benny at 2022/7/19 23:19.
 */
abstract class WebViewBridge: CommonBridge() {

    fun call(value: String) {
        val functionInfo = try {
            Json.decodeFromString<KlueFunctionInfo>(value)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        returnToJs(functionInfo, call(functionInfo))
    }

    abstract fun returnToJs(functionInfo: KlueFunctionInfo, result: KlueFunctionResult)

}