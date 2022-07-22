package com.bennyhuo.klue.webview

import com.bennyhuo.klue.common.invoke.KlueFunctionInfo
import com.bennyhuo.klue.common.invoke.KlueFunctionResult
import com.bennyhuo.klue.common.utils.KLUE_BRIDGE_NAME
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import platform.Foundation.NSLog
import platform.WebKit.WKWebView

/**
 * Created by benny at 2022/7/19 22:56.
 */
class IosWebViewBridge(val webView: WKWebView): WebViewBridge() {

    private val messageHandler = KlueMessageHandler(this)

    init {
        webView.configuration.userContentController.addScriptMessageHandler(messageHandler, KLUE_BRIDGE_NAME)
    }

    fun callNative(value: String) {
        NSLog("Klue: value, ${value.length} -- $value")
        call(value)
    }

    override fun returnToJs(functionInfo: KlueFunctionInfo, result: KlueFunctionResult) {
        webView.evaluateJavaScript("${functionInfo.callback}(${Json.encodeToString(result)})", null)
    }
}