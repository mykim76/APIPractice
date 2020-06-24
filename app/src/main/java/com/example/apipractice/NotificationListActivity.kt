package com.example.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class NotificationListActivity : BaseActivity() {
    override fun setValues() {

        imgNotification.visibility = View.GONE // 알림화면에서는 숨기기
    }

    override fun setupEvents() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)

        setValues()
        setupEvents()
    }
}
