package com.bennyhuo.klue.common

interface Bridge<T> {

    val target: T

    val name: String

    fun call(functionName: String, args: String): String

}