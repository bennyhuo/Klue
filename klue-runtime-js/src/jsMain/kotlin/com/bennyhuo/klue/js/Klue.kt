package com.bennyhuo.klue.js

import kotlin.js.Promise


/**
 * Created by benny.
 */
interface Klue {
    fun <T> callNative(className: String, functionName: String, args: String): Promise<T>
}
