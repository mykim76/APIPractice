package com.example.apipractice.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        Log.d("새 토큰 발급",p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) { //실제 푸시알림 수신시 실행되는 함수
        super.onMessageReceived(p0)

        Log.d("푸쉬알림 수신",p0.notification?.title)
    }
}