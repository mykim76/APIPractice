package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.apipractice.datas.Topic
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {
    
     var mTopicId = -1 //다른 화면에서 보내주는 주제 id 값 저장 변수
    lateinit var mTopic : Topic // 서버에서 받아온 주제 정보를 저장할 맴버변수

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

                val data = json.getJSONObject("data")
                val topic = data.getJSONObject("topic")

                val topicObj = Topic.getTopicFromJson(topic)

                mTopic = topicObj

                //==val replies = data.getJSONArray("replies")
                runOnUiThread {
                txtTopicTitle.text = mTopic.title
                    Glide.with(mContext).load(mTopic.imgUrl).into(imgTopic)
                }



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
