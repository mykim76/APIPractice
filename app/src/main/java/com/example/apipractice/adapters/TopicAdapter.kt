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

class TopicAdapter(context: Context, resId:Int, list: List<Topic>):
    ArrayAdapter<Topic>(context, resId,list) {

    val mContext = context
    val mList = list
    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
//        if(tempRow==null){
//            tempRow = inf.inflate(R.layout.room_list_item, null)
//        }

        tempRow?.let{
            //null이 아닐때 실행할 함수들

        }.let{
            //null일때 실행할 함수
            tempRow = inf.inflate(R.layout.topic_list_item, null)
        }

        var row = tempRow!!


//        val resourceId = context.resources.getIdentifier(dog.photo, "drawable", context.packageName)
//        dogPhoto.setImageResource(resourceId)

        val txtTopic = row.findViewById<TextView>(R.id.txtTopic)
        val imgUrl = row.findViewById<ImageView>(R.id.imgUril)

        //val data = mList.get(position)
        val data = mList[position]
        //val resourceId = mContext.resources.getIdentifier(data.imgUrl,"drawable",mContext.packageName)
        //imgUrl.setImageResource(resourceId)


        txtTopic.text = "${data.title}"
        Glide.with(mContext).load(data.imgUrl).into(imgUrl)

        return row

    }
}