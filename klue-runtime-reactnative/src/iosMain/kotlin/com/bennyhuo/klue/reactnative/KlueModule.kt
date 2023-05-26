package com.bennyhuo.klue.reactnative

import kotlinx.cinterop.ExportObjCClass
import platform.Foundation.NSError

@ExportObjCClass("KlueModule")
class KlueModule {

    fun callNative(
        value: String,
        resolve: (Any?) -> Unit,
        reject: (code: String, message: String, error: NSError?) -> Unit
    ) {
        println("callNative!!! ${value}")

        val result = ReactNativeBridge.call(value)
        if (result.code == 0) {
            resolve(result.data)
        } else {
            reject(result.code.toString(), result.data, null)
        }
    }
}