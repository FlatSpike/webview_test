package ru.flatspike.test

import android.app.Application
import android.content.Intent
import ru.flatspike.test.services.TimerService
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    companion object {
        // tick timer each 10 minutes
        val DEFAULT_PERIOD: Long = TimeUnit.MILLISECONDS.convert(10, TimeUnit.MINUTES)
        // run first tick after 30 seconds
        val DEFAULT_DELAY: Long = TimeUnit.MILLISECONDS.convert(30, TimeUnit.SECONDS)
    }

    override fun onCreate() {
        super.onCreate()

        // start timer service at application start
        startService(Intent(this, TimerService::class.java)
            .putExtra(TimerService.PERIOD, DEFAULT_PERIOD)
            .putExtra(TimerService.DELAY, DEFAULT_DELAY)
        )
    }
}