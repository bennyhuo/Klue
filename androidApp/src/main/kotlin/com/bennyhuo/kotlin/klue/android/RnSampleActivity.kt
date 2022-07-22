package com.bennyhuo.kotlin.klue.android

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import com.bennyhuo.klue.reactnative.KluePackage
import com.bennyhuo.klue.reactnative.ReactNativeBridge
import com.bennyhuo.kotlin.klue.android.databinding.ActivityRnBinding
import com.bennyhuo.kotlin.klue.sample.Utils
import com.bennyhuo.kotlin.klue.sample.UtilsImpl
import com.bennyhuo.kotlin.klue.sample.bridge
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactPackage
import com.facebook.react.ReactRootView
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.common.LifecycleState
import com.facebook.react.config.ReactFeatureFlags
import com.facebook.react.packagerconnection.PackagerConnectionSettings
import com.facebook.react.shell.MainReactPackage
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager


/**
 * Created by benny.
 */
const val TAG = "rn-sample"

class RnSampleActivity : Activity() {

    private val OVERLAY_PERMISSION_REQ_CODE = 1 // Choose any value

    private val binding by lazy {
        ActivityRnBinding.bind(findViewById(R.id.main_view))
    }

    private lateinit var reactRootView: ReactRootView
    private lateinit var reactInstanceManager: ReactInstanceManager

    private val bridge = ReactNativeBridge()

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
            }
        }
    }

    private fun reloadRnView() {
        ReactFeatureFlags.useTurboModules = false

        binding.rnContainer.removeAllViews()
        PackagerConnectionSettings(applicationContext).debugServerHost =
            binding.url.text.toString().ifEmpty { "localhost:8081" }

        reactRootView = ReactRootView(this)

        reactInstanceManager = ReactInstanceManager.builder()
            .setApplication(app)
            .setCurrentActivity(this)
            //.setBundleAssetName("index.bundle")
            .setJSMainModulePath("index.js")
            .addPackage(MainReactPackage())
            .addPackage(KluePackage(bridge))
            .setUseDeveloperSupport(BuildConfig.DEBUG)
            .setInitialLifecycleState(LifecycleState.RESUMED)
            .build()

        bridge.register(UtilsImpl().bridge())

        reactRootView.startReactApplication(reactInstanceManager, "KlueSample")
        binding.rnContainer.addView(reactRootView, -1, -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkOverlayPermission()
        setContentView(R.layout.activity_rn)
        reloadRnView()

        binding.reload.setOnClickListener {
            reloadRnView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted
                    Log.d(
                        TAG,
                        "onActivityResult() called with: requestCode = $requestCode, resultCode = $resultCode, data = $data"
                    )
                }
            }
        }
        reactInstanceManager.onActivityResult(this, requestCode, resultCode, data)
    }

    override fun onPause() {
        super.onPause()
        reactInstanceManager.onHostPause(this)
    }

    override fun onResume() {
        super.onResume()
        reactInstanceManager.onHostResume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        reactInstanceManager.onHostDestroy(this)
        reactRootView.unmountReactApplication()
    }

}