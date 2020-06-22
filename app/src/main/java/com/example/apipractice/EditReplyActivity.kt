package com.example.apipractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.apipractice.utils.ContextUtil
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_edit_reply.*
import org.json.JSONObject

class EditReplyActivity : BaseActivity() {

    var mTopicId = -1
    override fun setValues() {
        
        //의견 작성 후 버튼 클릭=>
        //의견을 등록 하시겠습니까? 확인 누르면 실체 등록 처리
//        val alertDialogBuilder = AlertDialog.Builder(mContext)
//        val builder = AlertDialog.Builder(mContext)
//        builder.setTitle("의견 등록")
//        builder.setMessage("의견을 등록 하겠습니까?")
//
//        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
//            //ContextUtil.setAutoLogin(mContext,false)
//
//            val opinion = txtOpinion.text.toString()
//            ServerUtil.postRequestTopicReply(mContext,opinion, object : ServerUtil.JsonResponseHandler{
//                override fun onResponse(json: JSONObject) {
//
//                    val code = json.getInt("code")
//                    if(code==200)
//                    {
//                        Log.d("의견 등록","의견등록")
//                    }
//                }
//
//            })
//            finish()
//
//        }
//
//        builder.setNegativeButton(android.R.string.no) { dialog, which ->
//
//        }
//        builder.show()
        
        //화면 진입시 첨부한 관련 정보 표시
        txtTopicTitle.text = intent.getStringExtra("topicTitle")
        txtMySideTitle.text = intent.getStringExtra("mySideTitle")
        mTopicId = intent.getIntExtra("topicId",-1)
        
    }

    override fun setupEvents() {

        btnPostReply.setOnClickListener {

            val opinion = edtOpinion.text.toString()

            val builder = AlertDialog.Builder(mContext)
            builder.setTitle("의견 등록")
            builder.setMessage("의견을 등록 하겠습니까?")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->


                ServerUtil.postRequestTopicReply(mContext, mTopicId,opinion, object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(json: JSONObject) {

                        val code = json.getInt("code")
                        if(code==200)
                        {
                            Log.d("의견 등록","의견등록")
                        }
                    }

                })
                finish()

            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reply)

        setupEvents()
        setValues()
    }
}
