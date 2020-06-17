package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : BaseActivity() {
    override fun setValues() {

    }

    override fun setupEvents() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setValues()
        setupEvents()
    }
}
