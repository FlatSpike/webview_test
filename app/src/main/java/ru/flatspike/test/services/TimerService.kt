package ru.flatspike.test.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder

class TimerService : Service() {

    companion object {
        const val DELAY = "delay"
        const val PERIOD = "period"
    }

    private inner class ListenersNotifier(
        val period: Long
    ) : Runnable {
        override fun run() {
            // notify all listeners wait period and start again
            for (listener in listeners) listener()
            handler.postDelayed(this, period)
        }
    }

    inner class LocalBinder : Binder() {
        val service: TimerService = this@TimerService
    }

    private val handler: Handler by lazy { Handler() }
    // all tasks should be running in the main thread, so we can use simple Set
    private val listeners: MutableSet<() -> Unit> = mutableSetOf()
    private var isStarted = false

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (!isStarted) {
            val extras = intent.extras!!
            val delay = extras.getLong(DELAY)
            val period = extras.getLong(PERIOD)

            isStarted = true

            handler.postDelayed(ListenersNotifier(period), delay)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? = LocalBinder()

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        listeners.clear()
    }

    fun addListener(listener: () -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: () -> Unit) {
        listeners.remove(listener)
    }
}