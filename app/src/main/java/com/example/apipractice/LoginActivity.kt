package com.example.apipractice

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun setValues() {
        btnLogin.setOnClickListener {


        }

    }

    override fun setupEvents() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setValues()
        setupEvents()
    }


}
