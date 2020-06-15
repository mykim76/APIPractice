package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this
    abstract fun setValues()
    abstract fun setupEvents()
}