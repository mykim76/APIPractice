package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EditReplyActivity : BaseActivity() {
    override fun setValues() {

    }

    override fun setupEvents() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reply)

        setupEvents()
        setValues()
    }
}
