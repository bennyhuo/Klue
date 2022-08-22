package com.bennyhuo.klue.common

import com.bennyhuo.klue.common.exception.BridgeNotFoundException
import com.bennyhuo.klue.common.invoke.KlueFunctionInfo
import com.bennyhuo.klue.common.invoke.KlueFunctionResult

/**
 * Created by benny at 2022/7/19 22:39.
 */
abstract class CommonBridge {

    private val bridges = HashMap<String, Bridge<*>>()

    fun register(bridge: Bridge<*>) {
        bridges[bridge.name] = bridge
    }

    operator fun Bridge<*>.unaryPlus() {
        register(this)
    }

    fun unregister(bridge: Bridge<*>) {
        bridges.remove(bridge.name)
    }

    fun unregister(bridgeName: String) {
        bridges.remove(bridgeName)
    }

    operator fun Bridge<*>.unaryMinus() {
        unregister(this)
    }

    fun call(functionInfo: KlueFunctionInfo): KlueFunctionResult {
        return try {
            val impl = bridges[functionInfo.className]
                ?: throw BridgeNotFoundException("${functionInfo.className}.${functionInfo.functionName}")

            val result = impl.call(functionInfo.functionName, functionInfo.parameters)
            KlueFunctionResult(0, result)
        } catch (e: Exception) {
            e.printStackTrace()
            KlueFunctionResult(-1, e.toString())
        }
    }
}