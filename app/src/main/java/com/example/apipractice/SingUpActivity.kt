package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sing_up.*
import org.json.JSONObject

class SingUpActivity : BaseActivity() {
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
                        } else {
                            txtCheckEmailResult.text = "중복 아이디임"

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
