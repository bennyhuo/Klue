package com.bennyhuo.klue.webview

import platform.WebKit.WKScriptMessage
import platform.WebKit.WKScriptMessageHandlerProtocol
import platform.WebKit.WKUserContentController
import platform.darwin.NSObject

class KlueMessageHandler(val bridge: IosWebViewBridge): NSObject(), WKScriptMessageHandlerProtocol {

    override fun userContentController(
        userContentController: WKUserContentController,
        didReceiveScriptMessage: WKScriptMessage
    ) {
        println(didReceiveScriptMessage.body)
        bridge.callNative(didReceiveScriptMessage.body as String)
    }
}