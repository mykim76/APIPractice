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
import com.example.apipractice.datas.Notification
import com.example.apipractice.datas.Topic
import com.example.apipractice.utils.TimeUtil

class NotificationAdapter(context: Context, resId:Int, list: List<Notification>):
    ArrayAdapter<Notification>(context, resId,list) {

    val mContext = context
    val mList = list
    val inf = LayoutInflater.from(mContext)



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if(tempRow==null){
            tempRow = inf.inflate(R.layout.notification_list_item, null)
        }


        var row = tempRow!!



        val txtTitle = row.findViewById<TextView>(R.id.txtTitle)
        val txtMessage = row.findViewById<TextView>(R.id.txtMessage)
        val txtTimeZone = row.findViewById<TextView>(R.id.txtMessage)
        val data = mList[position]


        txtTitle.text = "${data.title}"
        txtMessage.text = "${data.message}"
        txtTimeZone.text = TimeUtil.getTimeAgoFromCalendar(data.createdAt)

        //txtTimeZone.text = "${data.message}"

        return row

    }
}