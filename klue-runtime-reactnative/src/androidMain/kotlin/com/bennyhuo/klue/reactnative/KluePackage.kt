package com.bennyhuo.klue.reactnative

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class KluePackage : ReactPackage {
    override fun createNativeModules(reactApplicationContext: ReactApplicationContext): List<NativeModule> {
        return listOf(KlueModule(reactApplicationContext))
    }

    override fun createViewManagers(reactApplicationContext: ReactApplicationContext)
            : List<ViewManager<View, ReactShadowNode<*>>> {
        return emptyList()
    }
}