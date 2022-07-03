package com.bennyhuo.kotlin.klue

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}