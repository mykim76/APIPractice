package com.example.apipractice

import android.os.Bundle
import android.widget.Toast
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun setValues() {


    }

    override fun setupEvents() {
        btnLogin.setOnClickListener {
            val email = emailEdt.text.toString()
            val password = passwordEdt.text.toString()
            //서버에서 로그인 처리 불러오기
            ServerUtil.postRequestLogin(mContext,email,password,object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val codeNumber = json.getInt("code") //가장 큰 껍데기 json변수에서 code 이름의 정수값 추출
                    if(codeNumber == 200)
                    {
                        //로그인 성공
                    }
                    else
                    {
                        //로그인 실패 //kj_cho@nepp.kr
                        //UI 반영: 서버에서 알려주는 실패 사유를 토스트로 출력
                        //인터넷 통신 => 백그라운드 쓰레드에서 돌고 있음 => UI 접근시 강제 종료 됨
                        //=> UI 쓰레드가 실행해서 토스트 뛰우도록 해야 죽지 않음
                        runOnUiThread {
                            Toast.makeText(mContext, "${json.getString("message")}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })
        }

        btnSignUp.setOnClickListener {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setValues()
        setupEvents()
    }


}
