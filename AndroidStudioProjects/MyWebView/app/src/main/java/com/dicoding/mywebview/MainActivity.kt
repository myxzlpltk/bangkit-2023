package com.dicoding.mywebview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mywebview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }

                override fun onPageFinished(view: WebView, url: String) {
                    view.loadUrl("javascript:alert('Web Dicoding berhasil dimuat')")
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(view: WebView, url: String, message: String, result: android.webkit.JsResult): Boolean {
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                    result.confirm()
                    return true
                }
            }

            loadUrl("https://dicoding.com")
        }
    }
}