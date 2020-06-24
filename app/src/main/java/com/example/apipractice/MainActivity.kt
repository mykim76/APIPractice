package com.example.apipractice

import android.content.Intent
import android.drm.DrmStore
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.apipractice.adapters.TopicAdapter
import com.example.apipractice.datas.Topic
import com.example.apipractice.datas.User
import com.example.apipractice.utils.ContextUtil
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

        topicListView.setOnItemClickListener { parent, view, position, id ->

            val clickedTopic = topicList[position]

            val myIntent = Intent(mContext, ViewTopicDetailActivity::class.java)
            myIntent.putExtra("topic_id",clickedTopic.id)
            startActivity(myIntent)
        }
        btnLogout.setOnClickListener {
            //로그아웃이 눌리면 정말 로그아웃 할 것인지 확인 받기
            //저장된 토큰을 빈칸으로 돌려주자

//            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    switch (which){
//                        case DialogInterface.BUTTON_POSITIVE:
//                        //Yes button clicked
//                        break;
//
//                        case DialogInterface.BUTTON_NEGATIVE:
//                        //No button clicked
//                        break;
//                    }
//                }
//            };
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
//                .setNegativeButton("No", dialogClickListener).show();

            val alertDialogBuilder = AlertDialog.Builder(mContext)
            val builder = AlertDialog.Builder(mContext)
            builder.setTitle("로그아웃")
            builder.setMessage("정말 로그아웃 하겠습니까?")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                //ContextUtil.setAutoLogin(mContext,false)
                ContextUtil.setUserToken(mContext,"")

                val myIntent = Intent(mContext,LoginActivity::class.java)
                startActivity(myIntent)
                finish()

            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()


        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setValues()
        setupEvents()
    }
    

}
