package ru.flatspike.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import ru.flatspike.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.webviewMain.loadUrl("https://google.com/")

        setContentView(R.layout.activity_main)
    }
}
