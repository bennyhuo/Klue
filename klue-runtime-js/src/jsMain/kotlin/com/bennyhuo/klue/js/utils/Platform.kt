package com.bennyhuo.klue.js.utils

import kotlinx.browser.window
import kotlin.js.RegExp

fun isReactNative(): Boolean {
    return window.navigator.product === "ReactNative"
}