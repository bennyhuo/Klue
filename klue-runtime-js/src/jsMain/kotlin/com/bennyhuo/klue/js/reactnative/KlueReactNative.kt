package com.bennyhuo.klue.js.reactnative

import _reactNative
import com.bennyhuo.klue.js.KlueJsBridge
import com.bennyhuo.klue.common.exception.BridgeInvokeException
import com.bennyhuo.klue.common.invoke.KlueFunctionInfo
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.js.Promise

object KlueReactNative: KlueJsBridge {

    override fun <T> callNative(className: String, functionName: String, args: String): Promise<T> {
        return Promise { resolve, reject ->
            if (_reactNative == null) {
                reject(BridgeInvokeException("$className.$functionName", -2, "ReactNative is not initialized."))
                return@Promise
            }

            val klueModule = _reactNative.NativeModules.KlueModule

            val functionInfo = KlueFunctionInfo(
                className,
                functionName,
                args,
                ""
            )

            val value = Json.encodeToString(functionInfo)
            println("callNative: ${value.length} -- $value")

            val timeoutHandle = window.setTimeout({
                reject(BridgeInvokeException("$className.$functionName", -1, "Timeout"))
            }, 5000)

            val promise = if (isAndroid()) {
                klueModule.callNative(value)
            } else {
                klueModule.callNativeValue(value)
            } as Promise<T>

            promise.then(resolve)
                .catch(reject)
                .finally { window.clearTimeout(timeoutHandle) }
        }
    }

    private fun isAndroid() = _reactNative.Platform.OS == "android"

}