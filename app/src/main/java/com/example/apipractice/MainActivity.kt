package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apipractice.adapters.TopicAdapter
import com.example.apipractice.datas.Topic
import com.example.apipractice.datas.User
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val topicList = ArrayList<Topic>()

    lateinit var topicAdapter:TopicAdapter //선언만 우선 하고 형은 나중에 알려주겠어
    override fun setValues() {

        // 진행중인 토론 목록이 어떤게 있는지 무어보자
        ServerUtil.getRequestMainInfo(mContext,object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val topics = data.getJSONArray("topics") //[]안은 JSONArray 받기


                //for(topic in topics)
                for(i in 0..topics.length()-1)
                {
                    val topicJson = topics.getJSONObject(i)
                    val topic = Topic.getTopicFromJson(topicJson)//주제 하나에 대응되는 JSON을 넣어서 Topic 객체로 얻어내자
                    topicList.add(topic)//주제목록을 리스트뷰 재료로 추가

                }

                runOnUiThread {

                    topicAdapter = TopicAdapter(mContext, R.layout.topic_list_item,topicList)
                    topicListView.adapter = topicAdapter
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
