package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.apipractice.utils.ContextUtil
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sing_up.*
import org.json.JSONObject

class SingUpActivity : BaseActivity() {

    var isEmailOK = false;
    var isNickOK = false;
    var isPWOK = false;

    override fun setValues() {

    }

    override fun setupEvents() {
        btnCheckEmail.setOnClickListener {
            val email = etdEmail.text.toString()
            //서버에 중복 확인 요청
            ServerUtil.getRequestDuplicatedCheck(mContext,"EMAIL",email, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                    
                    val code = json.getInt("code")

                    runOnUiThread {
                        if (code == 200) {
                            txtCheckEmailResult.text = "사용해도 좋음"
                            isEmailOK = true
                        } else {
                            txtCheckEmailResult.text = "중복 아이디임"

                        }
                    }

                }

            })
        }

        btnCheckNick.setOnClickListener {

            val nick = etdNick.text.toString()

            ServerUtil.getRequestDuplicatedCheck(mContext,"NICK_NAME",nick, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                    val code  = json.getInt("code")
                    
                    runOnUiThread { 
                        
                        if(code == 200)
                        {
                            txtCheckNickResult.text="사용해도 좋음"
                            isNickOK = true;
                        }
                        else{
                            
                            txtCheckNickResult.text="중복 닉네임"
                        }
                    }
                }


            })
        }
        
        btnSignUp.setOnClickListener { 
            
            //회원가입API를 호출하기 전 자체 검사
            //1) 이메일 2) 닉네임 중복검사 3) 비번은 8글자 이상이어야 함 => 틀린 경우가 발견되는 케이스와 내용을 토스트로 띄우기
            if(!isEmailOK){
                Toast.makeText(mContext,"이메일 중복검사를 통과해야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!isNickOK){
                Toast.makeText(mContext,"닉네임 중복검사를 통과해야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pw = etdPW.text.toString()
            val email = etdEmail.text.toString()
            val nick = etdNick.toString()
            //Log.d("email:", email)
            //if(pw.length>=8) {isPWOK = true}

            if(pw.length<8){
                Toast.makeText(mContext,"비밀번호는 8자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //서버에 회원가입 시키기
            ServerUtil.putRequestSingUp(mContext,email,nick,pw, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                    val code = json.getInt("code")

                    runOnUiThread {
                        if (code == 200) {
                            //Toast.makeText(mContext,"회원가입 성공", Toast.LENGTH_SHORT).show()
                            val data = json.getJSONObject("data")
                            val token = data.getString("token") //토큰 추출

                            //폰에 저장해두는게 편리 =>ContextUtil
                            ContextUtil.setUserToken(mContext,token)

                        } else {
                            Toast.makeText(mContext,"실패", Toast.LENGTH_SHORT).show()
                            //finish()
                        }
                    }
                }

            })


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        setValues()
        setupEvents()
    }
}
