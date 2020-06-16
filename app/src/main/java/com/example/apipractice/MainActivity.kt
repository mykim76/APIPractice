package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {
    override fun setValues() {

        ServerUtil.getRequestUserInfo(mContext, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                val code = json.getInt("code")
                if(code==200)
                {
                    val data = json.getJSONObject("data")
                    val user = data.getJSONObject("user")
                    val nick = user.getString("nick_name")
                    runOnUiThread {
                        txtUserNickName.text = nick
                    }
                }
            }

        })
    }

    override fun setupEvents() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setValues()
        setupEvents()
    }
}
