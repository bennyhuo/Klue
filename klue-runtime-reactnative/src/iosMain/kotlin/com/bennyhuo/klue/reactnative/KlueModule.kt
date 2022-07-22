package com.bennyhuo.klue.reactnative

import com.bennyhuo.klue.reactnative.ReactNativeBridge
import kotlinx.cinterop.ExportObjCClass
import platform.Foundation.NSError

@ExportObjCClass("KlueModule")
class KlueModule {

    private val bridge = ReactNativeBridge()

    fun getBridge() = bridge

    fun callNative(
        value: String,
        resolve: (Any?) -> Unit,
        reject: (code: String, message: String, error: NSError?) -> Unit
    ) {
        println("callNative!!! ${value}")

        val result = bridge.call(value)
        if (result.code == 0) {
            resolve(result.data)
        } else {
            reject(result.code.toString(), result.data, null)
        }
    }

}