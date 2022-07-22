package com.bennyhuo.klue.js

import com.bennyhuo.klue.js.reactnative.KlueReactNative
import com.bennyhuo.klue.js.utils.isReactNative
import com.bennyhuo.klue.js.webview.KlueWebView
import kotlin.js.Promise

object KlueJs {

    private val target: Klue = if (isReactNative()) KlueReactNative else KlueWebView

    fun <T> callNative(className: String, functionName: String, args: String): Promise<T> {
        return target.callNative(className, functionName, args)
    }

}