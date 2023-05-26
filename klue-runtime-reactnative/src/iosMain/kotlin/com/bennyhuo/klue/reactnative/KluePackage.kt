package com.bennyhuo.klue.reactnative

import com.facebook.react.native.RCTMethodInfo
import kotlinx.cinterop.Arena
import kotlinx.cinterop.alloc
import kotlinx.cinterop.cstr
import kotlinx.cinterop.nativeHeap

private val methodInfo: RCTMethodInfo = nativeHeap.alloc<RCTMethodInfo>().apply {
    isSync = false
    jsName = "".cstr.getPointer(Arena())
    objcName = ("callNativeValue:(NSString *)value " +
            "resolve:(RCTPromiseResolveBlock)resolve " +
            "reject:(RCTPromiseRejectBlock)reject").cstr.getPointer(Arena())
}

fun ReactNativeBridge.methodInfo(): RCTMethodInfo {
    return methodInfo
}