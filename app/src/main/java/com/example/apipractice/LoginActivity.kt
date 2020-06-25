package com.example.apipractice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.apipractice.utils.ContextUtil
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun setValues() {

        notiFramlayout.visibility = View.GONE


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
                        val data = json.getJSONObject("data")
                        val token = data.getString("token") //토큰 추출

                        //폰에 저장해두는게 편리 =>ContextUtil
                        ContextUtil.setUserToken(mContext,token)

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)

                        finish()
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

            val myIntent = Intent(mContext, SingUpActivity::class.java)
            startActivity(myIntent)

        }
        
        // 자동 로그인 체크박스의 값 변화 이벤트
        chkAutoLogin.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("체크박스 값",isChecked.toString())

            //체크가 됐다면 =>
            //체크가 해제됐다면 -> ContextUtil로 자동 로그인 false로 저장
            ContextUtil.setAutoLogin(mContext,isChecked)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setValues()
        setupEvents()
    }


}
