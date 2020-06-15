package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sing_up.*

class SingUpActivity : BaseActivity() {
    override fun setValues() {

    }

    override fun setupEvents() {
        btnCheckEmail.setOnClickListener {
            val email = etdEmail.text.toString()
            //서버에 중복 확인 요청
            ServerUtil.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        setValues()
        setupEvents()
    }
}
