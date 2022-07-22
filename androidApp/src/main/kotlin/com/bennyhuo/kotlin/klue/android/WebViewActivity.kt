package com.bennyhuo.kotlin.klue.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bennyhuo.klue.webview.AndroidWebViewBridge
import com.bennyhuo.kotlin.klue.sample.UtilsImpl
import com.bennyhuo.kotlin.klue.android.databinding.ActivityWebviewBinding
import com.bennyhuo.kotlin.klue.sample.bridge

class WebViewActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityWebviewBinding.bind(findViewById(R.id.main_view))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        WebView.setWebContentsDebuggingEnabled(true)
        binding.webView.apply {
            webViewClient = object : WebViewClient() {}
            webChromeClient = object : WebChromeClient() {}
            settings.javaScriptEnabled = true

            loadUrl(binding.url.text.toString())
        }

        AndroidWebViewBridge(binding.webView).register(UtilsImpl().bridge())

        binding.reload.setOnClickListener {
            binding.webView.loadUrl(binding.url.text.toString())
        }
    }
}
