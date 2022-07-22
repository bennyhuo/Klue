package com.bennyhuo.kotlin.klue.android

import android.app.Application

/**
 * Created by benny.
 */
lateinit var app: App
    private set

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

}