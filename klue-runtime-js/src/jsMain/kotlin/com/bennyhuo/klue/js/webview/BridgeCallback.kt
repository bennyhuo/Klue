package com.bennyhuo.klue.js.webview

interface BridgeCallback<T> {
    fun onSuccess(returnValue: T)

    fun onError(code: Int, message: String)
}