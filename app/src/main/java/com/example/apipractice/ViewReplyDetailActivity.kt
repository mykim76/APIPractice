package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.apipractice.datas.TopicReply
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {
    
    var mReplyId = -1 //어떤 의견을 보는지 기록

    lateinit var mReply: TopicReply

    val mReReplyList = ArrayList<TopicReply>() //서버에서 보내주는 답글 목록을 저장할 배열
    
    override fun setValues() {
        mReplyId = intent.getIntExtra("replyId",-1)

    }

    override fun setupEvents() {
        btnPostReply.setOnClickListener {
            val inputContent = edtContent.text.toString()

            if(inputContent.length<5){
                Toast.makeText(mContext,"답글길이는 5자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ServerUtil.postRequestReReply(mContext,mReplyId, inputContent, object :ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                }

            })

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)

        setValues()
        setupEvents()
    }

    override fun onResume() {
        super.onResume()
        getReplyDetailFromServer()
    }
    fun getReplyDetailFromServer(){

        ServerUtil.getRequestReplyDetail(mContext, mReplyId, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {


                var code = json.getInt("code")
                Log.d("코드", code.toString())
                val data = json.getJSONObject("data")
                val reply = data.getJSONObject("reply")

                mReply = TopicReply.getTopicReplyFromJason(reply)
                
                //리스트 채워넣고 => 새로고침 하자

                //==val replies = data.getJSONArray("replies")
                runOnUiThread {
                    txtSelectedSide.text = mReply.selectedSide.title
                    txtContent.text = "(${mReply.content})"
                    txtWriterNick.text = mReply.writer.nickName
                    //


                    //의견목록을 리스트뷰에 뿌려주기
//                    mReplyAdapter = ReplyAdapter(mContext,R.layout.topic_reply_list_item,mTopic.replyList)
//
//                    replyListView.adapter = mReplyAdapter
                }

            }

        })

    }
}
