package com.example.apipractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.apipractice.adapters.ReplyAdapter
import com.example.apipractice.datas.Topic
import com.example.apipractice.datas.TopicReply
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import kotlinx.android.synthetic.main.topic_reply_list_item.*
import org.json.JSONObject
import java.util.*

class ViewTopicDetailActivity : BaseActivity() {
    
     var mTopicId = -1 //다른 화면에서 보내주는 주제 id 값 저장 변수
    lateinit var mTopic : Topic // 서버에서 받아온 주제 정보를 저장할 맴버변수
    lateinit var mReplyAdapter: ReplyAdapter

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
                    txtFirstSideTitle.text = mTopic.sideList[0].title
                    txtSecondSideTitle.text = mTopic.sideList[1].title
                    //특표수
                    txtFirstViewCount.text = mTopic.sideList[0].vote_count.toString()
                    txtSecondViewCount.text = mTopic.sideList[1].vote_count.toString()
                    //어디 투표했는지 표시

                    if(mTopic.mySelectedSideIndex==-1){
                        //미선택
                        btnFirstVote.text="투표하기"
                        btnSecondVote.text="투표하기"
                    }
                    else if(mTopic.mySelectedSideIndex==0){
                        //미선택
                        btnFirstVote.text="투표취소"
                        btnSecondVote.text="갈아타기"
                    }
                    else if(mTopic.mySelectedSideIndex==1){
                        //미선택
                        btnFirstVote.text="갈아타기"
                        btnSecondVote.text="투표취소"
                    }

                    //


                    //의견목록을 리스트뷰에 뿌려주기
                    mReplyAdapter = ReplyAdapter(mContext,R.layout.topic_reply_list_item,mTopic.replyList)

                    replyListView.adapter = mReplyAdapter
                }



            }

        })
        
    }
    override fun setupEvents() {
        //의견 등록하기
        btnPostReply.setOnClickListener {


            mTopic.mySideInfo?.let {
                //선택 진영이 있을 때만 의견 작성으로 이동
                val myIntent = Intent(mContext, EditReplyActivity::class.java)
                myIntent.putExtra("topicTitle", mTopic.title)
                myIntent.putExtra("mySideTitle", it.title)
                startActivity(myIntent)
            }.let {
                if(it == null) {
                    Toast.makeText(mContext, "투표를 해야만 의견 작성이 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }


        btnFirstVote.setOnClickListener {
            val id = mTopic.sideList[0].id
            ServerUtil.postRequestVote(mContext,id, object : ServerUtil.JsonResponseHandler{

                override fun onResponse(json: JSONObject) {
                    val code = json.getInt("code")

                    val msg = json.getString("message")

                    getTopicDetailFromServer()
                    

//                    runOnUiThread {
//                        if (code == 20) {
//                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
//                            finish()
//                        } else {
//                            Toast.makeText(mContext, "투표 실패", Toast.LENGTH_SHORT).show()
//                            finish()
//                        }
//                    }
                }

            })


        }


        btnSecondVote.setOnClickListener {
            val id = mTopic.sideList[1].id
            ServerUtil.postRequestVote(mContext,id,object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")
                    val msg = json.getString("message")

                    getTopicDetailFromServer()

//                    runOnUiThread {
//                        if (code == 20) {
//                            Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show()
//                            finish()
//                        }else{
//                            Toast.makeText(mContext,"투표 실패",Toast.LENGTH_SHORT).show()
//                            finish()
//                        }
//                    }


                }

            })



        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)

        setValues()
        setupEvents()
    }
}
