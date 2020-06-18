package com.example.apipractice.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class TopicReply {

    var id = 0
    var topicId = 0
    var sideId = 0
    var userId = 0
    var content = ""

    lateinit var writer : User
    val createdAt = Calendar.getInstance() //작성 일시를 시간형태로 저장(기본:현재시간)

    companion object {
        //json 덩어리 input => 내용이 모두 적힌 TopicSide 객체 리턴
        fun getTopicReplyFromJason(json: JSONObject):TopicReply{

            val tr = TopicReply()
            tr.id = json.getInt("id")
            tr.topicId = json.getInt("topic_id")
            tr.sideId = json.getInt("side_id")
            tr.userId = json.getInt("user_id")
            tr.content = json.getString("content")

            val userObj = json.getJSONObject("user")

            tr.writer = User.getUserFromJson(userObj)

            //의견 작성 시간 1)String
            val createdAtStr = json.getString("created_at")
            
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")//string 분석할 양식을 클래스로 생성
            tr.createdAt.time= sdf.parse(createdAtStr)!!//파싱중인 의견 작성시간을 서버에서 알려준 작성시간으로 대입

            return tr
        }

    }
}