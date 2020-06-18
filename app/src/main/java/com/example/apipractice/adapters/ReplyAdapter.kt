package com.example.apipractice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.apipractice.R
import com.example.apipractice.datas.Topic
import com.example.apipractice.datas.TopicReply
import java.text.SimpleDateFormat

class ReplyAdapter(context: Context, resId:Int, list: List<TopicReply>):
    ArrayAdapter<TopicReply>(context, resId,list)  {
    val mContext = context
    val mList = list
    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        tempRow?.let{
            //null이 아닐때 실행할 함수들

        }.let{
            //null일때 실행할 함수
            tempRow = inf.inflate(R.layout.topic_reply_list_item, null)
        }

        var row = tempRow!!

        val txtWriter = row.findViewById<TextView>(R.id.txtWriter)
        val txtContent = row.findViewById<TextView>(R.id.txtContent)
        val txtWriteTime = row.findViewById<TextView>(R.id.txtWriteTime)

        val data = mList[position]


        txtWriter.text = "${data.writer.nickName}"
        txtContent.text = "${data.content}"
        
        val sdf = SimpleDateFormat("M월 d일 a h시 m분")

        txtWriteTime.text = sdf.format(data.createdAt.time) //?월?일 오전/오후 ?시 ?분 출력

        return row
    }
}