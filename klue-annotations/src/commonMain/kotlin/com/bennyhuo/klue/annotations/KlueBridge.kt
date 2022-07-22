package com.bennyhuo.klue.annotations

/**
 * Created by benny.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class KlueBridge(val name: String = "")