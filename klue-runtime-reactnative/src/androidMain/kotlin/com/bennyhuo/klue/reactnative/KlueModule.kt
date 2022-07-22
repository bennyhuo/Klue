package com.bennyhuo.klue.reactnative

import android.util.Log
import com.bennyhuo.klue.reactnative.ReactNativeBridge
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

const val TAG = "KLUE-RN"

class KlueModule(
    reactApplicationContext: ReactApplicationContext,
    private val bridge: ReactNativeBridge
) : ReactContextBaseJavaModule(reactApplicationContext) {

    override fun getName(): String {
        return "KlueModule"
    }

    @ReactMethod
    fun callNative(value: String, promise: Promise) {
        Log.d(TAG, "callNative() called with: value = $value")
        val result = bridge.call(value)
        if (result.code == 0) {
            promise.resolve(result.data)
        } else {
            promise.reject(result.code.toString(), result.data, null)
        }
    }

}

