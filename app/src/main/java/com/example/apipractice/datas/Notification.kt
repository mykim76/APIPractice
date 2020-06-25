package com.example.apipractice.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Notification {

    var id = 0
    var receiveUserId=0
    var actUserId = 0
    var title = ""
    var type = ""
    var message = ""
    var referenceUi = ""
    var focusObjectId = 0
    var createdAt = Calendar.getInstance()

    companion object {

        fun getNotificationFromJson(json: JSONObject): Notification {

            var noti = Notification()

            noti.id = json.getInt("id")
            noti.receiveUserId = json.getInt("receive_user_id")
            noti.actUserId = json.getInt("act_user_id")
            noti.title = json.getString("title")
            noti.message = json.getString("message")
            noti.referenceUi = json.getString("reference_ui")
            noti.focusObjectId = json.getInt("focus_object_id")

            val createdAtStr = json.getString("created_at")

            val parsingFormat = SimpleDateFormat("YYYY-MM-DD HH:mm:ss")

            noti.createdAt.time = parsingFormat.parse(createdAtStr)

            val myPhoneTimeZone = noti.createdAt.timeZone
            val timeOffset = myPhoneTimeZone.rawOffset / 1000/60/60
            noti.createdAt.add(Calendar.HOUR, timeOffset)

            return noti

        }
    }
}