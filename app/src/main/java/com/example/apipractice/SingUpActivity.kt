package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        etdNick.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                txtCheckNickResult.text = "닉네임 중복 검사를 해주세요"
                isNickOK = false
            }

        })
        // 이메일 입력값이 변경되면 무조건 다시 검사를 받으라고 문구 / Boolean 변경

        // 에디트객체에 이벤트 추가
        etdEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("바뀐 이메일", s.toString())
                //이메일 검사 필요
                txtCheckEmailResult.text ="이메일 중복 검사를 해주세요"
                isEmailOK = false//이메일 사용 불가 처리
                
            }

        })
        
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
            val nick = etdNick.text.toString()
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
