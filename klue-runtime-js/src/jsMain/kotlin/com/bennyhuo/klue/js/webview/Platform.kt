package com.bennyhuo.klue.js.webview

import kotlinx.browser.window
import kotlin.js.RegExp

internal fun testUa(reg: String) = RegExp(reg.lowercase()).test(window.navigator.userAgent.lowercase())

internal fun isIos() = testUa("iP(hone|od|ad)")

internal fun isAndroid() = testUa("Android")