package ru.flatspike.test.activities

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import ru.flatspike.test.databinding.ActivityMainBinding
import ru.flatspike.test.dialogs.YahooPromotionDialog
import ru.flatspike.test.services.TimerService
import ru.flatspike.test.services.TimerService.LocalBinder

class MainActivity : AppCompatActivity() {

    companion object {
        const val DIALOG_TAG_YAHOO = "yahoo"
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

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var timerService: TimerService? = null
    private var isTimerServiceBound = false

    private val timerListener = {
        // show yahoo promotion dialog when time has come
        YahooPromotionDialog().show(this@MainActivity.supportFragmentManager, DIALOG_TAG_YAHOO)
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            timerService = (binder as LocalBinder).service
            timerService?.addListener(timerListener)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            timerService = null
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

    override fun onResume() {
        super.onResume()

        // bind to service only if activity is active
        isTimerServiceBound = bindService(
            Intent(this, TimerService::class.java),
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onPause() {
        super.onPause()

        // remove service listeners and unbind service if activity is not longer active
        timerService?.removeListener(timerListener)

        if (isTimerServiceBound) {
            unbindService(serviceConnection)
        }
    }
}
