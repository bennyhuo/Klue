package com.bennyhuo.kotlin.klue.android

import com.bennyhuo.klue.common.CommonBridge
import com.bennyhuo.kotlin.klue.sample.UtilsImpl
import com.bennyhuo.kotlin.klue.sample.bridge

/**
 * Created by benny.
 */
fun CommonBridge.registerAllBridges() {
    +UtilsImpl().bridge()
    +UserApiImpl().bridge()
}