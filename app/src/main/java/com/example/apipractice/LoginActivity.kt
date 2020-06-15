package com.example.apipractice

import android.os.Bundle
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun setValues() {
        btnLogin.setOnClickListener {
            val email = emailEdt.text.toString()
            val password = passwordEdt.text.toString()
            //서버에서 로그인 처리 불러오기
            ServerUtil.postRequestLogin(mContext,email,password,null)
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
