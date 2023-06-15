package com.bennyhuo.klue.js.webview

import com.bennyhuo.klue.common.exception.BridgeInvokeException
import com.bennyhuo.klue.common.invoke.KlueFunctionInfo
import com.bennyhuo.klue.common.utils.KLUE_BRIDGE_NAME
import com.bennyhuo.klue.js.KlueJsBridge
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.get
import kotlin.js.Date
import kotlin.js.Promise

object KlueWebView : KlueJsBridge {

    private var callbackId = 0

    override fun <T> callNative(className: String, functionName: String, args: String): Promise<T> {
        return Promise<T> { resolve, reject ->
            val functionInfo = KlueFunctionInfo(
                className,
                functionName,
                args,
                "__klue_${className}_${functionName}_${Date().getTime()}_${callbackId++}"
            )

            val timeoutHandle = window.setTimeout({
                window.asDynamic()[functionInfo.callback] = ""
                reject(
                    BridgeInvokeException(
                        "$className.$functionName",
                        -1,
                        "Timeout"
                    )
                )
            }, 5000)


            window.asDynamic()[functionInfo.callback] = { returnValue: dynamic ->
                window.clearTimeout(timeoutHandle)
                window.asDynamic()[functionInfo.callback] = ""

                if (returnValue.code == 0) {
                    resolve(JSON.parse(returnValue.data))
                } else {
                    reject(
                        BridgeInvokeException(
                            "$className.$functionName",
                            returnValue.code,
                            returnValue.data
                        )
                    )
                }
            }

            val value = Json.encodeToString(functionInfo)
            println("callNative: ${value.length} -- $value")
            if (isAndroid()) {
                window[KLUE_BRIDGE_NAME].callNative(value)
            } else {
                window["webkit"].messageHandlers[KLUE_BRIDGE_NAME].postMessage(value)
            }
        }
    }


}