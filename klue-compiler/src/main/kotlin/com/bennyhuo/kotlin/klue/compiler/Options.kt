package com.bennyhuo.kotlin.klue.compiler

class Options(rawOptions: Map<String, String>) {
    val frameworkName: String by rawOptions
    val wrapperFrameworkName: String by rawOptions
    val wrapperSourceDir: String by rawOptions
    val projectDir: String by rawOptions
}

