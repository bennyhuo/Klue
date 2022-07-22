package com.bennyhuo.klue.js.reactnative

import com.bennyhuo.klue.js.Klue
import com.bennyhuo.klue.common.exception.BridgeInvokeException
import com.bennyhuo.klue.common.invoke.KlueFunctionInfo
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.js.Promise

object KlueReactNative: Klue {
    override fun <T> callNative(className: String, functionName: String, args: String): Promise<T> {
        return Promise { resolve, reject ->
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
                NativeModules.KlueModule.callNative(value)
            } else {
                NativeModules.KlueModule.callNativeValue(value)
            } as Promise<T>

            promise.then(resolve)
                .catch(reject)
                .finally { window.clearTimeout(timeoutHandle) }
        }
    }
}