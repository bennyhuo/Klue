package com.bennyhuo.klue.webview

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.bennyhuo.klue.common.invoke.KlueFunctionInfo
import com.bennyhuo.klue.common.invoke.KlueFunctionResult
import com.bennyhuo.klue.common.utils.KLUE_BRIDGE_NAME
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Created by benny at 2022/7/19 22:56.
 */
class AndroidWebViewBridge(val webView: WebView): WebViewBridge() {

    init {
        webView.addJavascriptInterface(this, KLUE_BRIDGE_NAME)
    }

    @JavascriptInterface
    fun callNative(value: String) {
        Log.d("Klue", "value, ${value.length} -- $value")
        call(value)
    }

    override fun returnToJs(functionInfo: KlueFunctionInfo, result: KlueFunctionResult) {
        webView.post {
            webView.evaluateJavascript("${functionInfo.callback}(${Json.encodeToString(result)})", null)
        }
    }
}