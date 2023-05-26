package com.bennyhuo.kotlin.klue.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bennyhuo.kotlin.klue.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.bind(findViewById(R.id.main_view))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(binding) {
            openWebViewSample.setOnClickListener {
                startActivity(Intent(this@MainActivity, WebViewActivity::class.java))
            }

            openRnSample.setOnClickListener {
                startActivity(Intent(this@MainActivity, RnSampleActivity::class.java))
            }
        }
    }
}
