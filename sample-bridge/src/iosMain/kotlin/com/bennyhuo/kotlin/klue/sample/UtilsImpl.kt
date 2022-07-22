package com.bennyhuo.kotlin.klue.sample

class UtilsImpl : Utils {
    val TAG = "utils-impl"

    override fun platform() = "iOS!!"

    override fun testParameters(int: Int, long: Long, float: Float, double: Double, string: String): ReturnObject {
        return ReturnObject(
            int, long, float, double, string
        )
    }
}