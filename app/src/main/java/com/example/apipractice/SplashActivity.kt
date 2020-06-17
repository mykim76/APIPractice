package com.example.apipractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.apipractice.utils.ContextUtil
import com.example.apipractice.utils.ServerUtil
import org.json.JSONObject

class SplashActivity : BaseActivity() {
    override fun setValues() {
        Handler().postDelayed({
            val isAutoLogin = ContextUtil.isAutoLogin(mContext) //자동 로그인 희망 여부
            val token = ContextUtil.getUserToken(mContext) //저장된 토큰값

            //자동로그인 && 토큰도 저장
            if(isAutoLogin&&token !="")
            {
                //서버에게 저장된 토큰으로 내 정보 불러오기
//            val myIntent = Intent(mContext, MainActivity::class.java)
//            startActivity(myIntent)
//            finish()

                ServerUtil.getRequestUserInfo(mContext,object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(json: JSONObject) {
                        val code = json.getInt("code")
                        if(code==200)
                        {
                            //토큰이 유효해서 정보를 잘 받아옴, 메인 이동
                            val myIntent = Intent(mContext, MainActivity::class.java)
                            startActivity(myIntent)
                            finish()
                        }
                        else
                        {
                            //토큰으로 내 정보 조회 실패 => 로그인 화면 이동

                            val myIntent = Intent(mContext,LoginActivity::class.java)
                            startActivity(myIntent)
                            finish()
                        }
                    }
                })
            }
            else
            {
                //로그인 화면으로 이동 =>2초뒤에 이동해야 함
                val myIntent = Intent(mContext,LoginActivity::class.java)
                startActivity(myIntent)

                finish()
            }

        },2000)



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
