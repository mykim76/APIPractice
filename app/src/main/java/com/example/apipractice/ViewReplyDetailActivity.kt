package com.example.apipractice

import android.content.Context
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.apipractice.adapters.ReReplyAdapter
import com.example.apipractice.adapters.ReplyAdapter
import com.example.apipractice.datas.TopicReply
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {
    
    var mReplyId = -1 //어떤 의견을 보는지 기록

    lateinit var mReply: TopicReply

    val mReReplyList = ArrayList<TopicReply>()

    lateinit var mReplyAdapter : ReReplyAdapter
    
    override fun setValues() {

        setTitle("의견 상세 보기")
        
        mReplyId = intent.getIntExtra("replyId",-1)
        mReplyAdapter = ReReplyAdapter(mContext,R.layout.topic_reply_list_item, mReReplyList)
        replyListView.adapter = mReplyAdapter

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

                    runOnUiThread {
                        edtContent.setText("")
                        Toast.makeText(mContext,"답글을 등록했습니다",Toast.LENGTH_SHORT).show()
                        getReplyDetailFromServer()

                        //리스트뷰의 스크롤을 맨 밑으로 이동
                        //replyListView.smoothScrollToPosition(mReReplyList.size-1)
                        replyListView.smoothScrollToPosition(mReReplyList.size-1)

                        //키보드 숨기기
//                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                        imm.hideSoftInputFromWindow(Window.ke)
                    }

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
                //reply내부의 답글 목록을 JSONArray로 채워넣기
                val replies = reply.getJSONArray("replies")
                mReReplyList.clear()//초기화
                for(i in 0.. replies.length()-1)
                {
//                    val reply = replies.getJSONObject(i)
//                    val topicReply = TopicReply.getTopicReplyFromJason(reply)
                    val topicReply = TopicReply.getTopicReplyFromJason(replies.getJSONObject(i))
                    mReReplyList.add(topicReply)
                }




                //리스트 채워넣고 => 새로고침 하자

                //==val replies = data.getJSONArray("replies")
                runOnUiThread {
                    txtSelectedSide.text = mReply.selectedSide.title
                    txtContent.text = "(${mReply.content})"
                    txtWriterNick.text = mReply.writer.nickName
                    //


                    //의견목록을 리스트뷰에 뿌려주기
//                    mReplyAdapter = ReReplyAdapter(mContext,R.layout.topic_reply_list_item,mReReplyList)
//
//                    replyListView.adapter = mReplyAdapter
                    mReplyAdapter.notifyDataSetChanged()
                }

            }

        })

    }
}
