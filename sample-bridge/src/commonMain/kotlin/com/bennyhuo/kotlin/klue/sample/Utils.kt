package com.bennyhuo.kotlin.klue.sample

import com.bennyhuo.klue.annotations.KlueBridge

@KlueBridge
interface Utils {

    fun platform(): String

    fun testParameters(
        int: Int,
        long: Long,
        float: Float,
        double: Double,
        string: String
    ): ReturnObject

}