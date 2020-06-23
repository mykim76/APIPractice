package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apipractice.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {
    
    var mReplyId = -1 //어떤 의견을 보는지 기록
    
    override fun setValues() {
        mReplyId = intent.getIntExtra("replyId",-1)

    }

    override fun setupEvents() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)


    }

    override fun onResume() {
        super.onResume()
        getReplyDetailFromServer()
    }
    fun getReplyDetailFromServer(){

        ServerUtil.getRequestReplyDetail(mContext, mReplyId, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

            }

        })

    }
}
