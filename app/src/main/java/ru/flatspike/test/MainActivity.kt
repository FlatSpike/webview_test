package ru.flatspike.test

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import ru.flatspike.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
        ): Boolean = handleUrl(request.url)

        // for API level < 24
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean = handleUrl(Uri.parse(url))

        private fun handleUrl(url: Uri): Boolean {
            // show google page inside WebView
            if (url.host == "www.google.com") return false

            // if we move away from google then open browser instead
            startActivity(Intent(Intent.ACTION_VIEW, url))

            return true
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webviewMain.settings.javaScriptEnabled = true
        binding.webviewMain.webViewClient = MyWebViewClient()
        binding.webviewMain.loadUrl("https://www.google.com/")
    }
}
