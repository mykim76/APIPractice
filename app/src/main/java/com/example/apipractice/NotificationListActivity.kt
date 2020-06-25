package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.apipractice.adapters.NotificationAdapter
import com.example.apipractice.datas.Notification
import com.example.apipractice.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_notification_list.*
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import kotlinx.android.synthetic.main.activity_view_reply_detail.txtContent
import kotlinx.android.synthetic.main.notification_list_item.*
import org.json.JSONObject

class NotificationListActivity : BaseActivity() {

    val mNotiList = ArrayList<Notification>()
    lateinit var mNotiAdapter : NotificationAdapter

    override fun setValues() {

        imgNotification.visibility = View.GONE // 알림화면에서는 숨기기
        notiFramlayout.visibility = View.GONE // 숨김

        mNotiAdapter = NotificationAdapter(mContext,R.layout.notification_list_item,mNotiList)
        notificationListView.adapter = mNotiAdapter
    }

    override fun setupEvents() {

    }

    override fun onResume() {
        super.onResume()
        getNotificationFromServer()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)

        setValues()
        setupEvents()
    }

    fun getNotificationFromServer(){

        ServerUtil.getRequestNotification(mContext,true, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val notis = data.getJSONArray("notifications")


                mNotiList.clear()
                for(i in 0..notis.length()-1){

                    val noti = Notification.getNotificationFromJson(notis.getJSONObject(i))
                    mNotiList.add(noti)

                }

                //알림을 받았으면, 받은 최신 알림의 id를 서버로 전파
                //여기까지 알림을 읽었다고 서버에 알려줌 (unread_noti_count를 0으로 셋팅)
                ServerUtil.postRequestNotification(mContext, mNotiList[0].id,null) //mNotiList[0].id: 최상위 id=가장마지막 알림.

                runOnUiThread {
                    mNotiAdapter.notifyDataSetChanged()
                }
            }

        })

    }
}
