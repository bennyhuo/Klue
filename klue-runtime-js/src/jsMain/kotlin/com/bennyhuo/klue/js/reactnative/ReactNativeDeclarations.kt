package com.bennyhuo.klue.js.reactnative

/**
 * Created by benny.
 */
// use lazy to postpone the initialization, for 'require' may not be defined
internal val reactNative by lazy {
    js("require('react-native')")
}

internal val NativeModules = reactNative.NativeModules

internal val Platform = reactNative.Platform