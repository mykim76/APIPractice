package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.apipractice.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {
    
     var mTopicId = -1 //다른 화면에서 보내주는 주제 id 값 저장 변수

    override fun setValues() {

        mTopicId = intent.getIntExtra("topic_id", -1)
        if(mTopicId==-1){
            Toast.makeText(mContext,"잘못된 접근",Toast.LENGTH_SHORT).show()
            return
        }

        //서버에 해당 토픽 진행상황 조회
        getTopicDetailFromServer()


    }

    fun getTopicDetailFromServer(){ //진행상황 받아오는 함수

        ServerUtil.getRequestTopicDetail(mContext,mTopicId,object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {


            }

        })
        
    }
    override fun setupEvents() {



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)

        setValues()
        setupEvents()
    }
}
