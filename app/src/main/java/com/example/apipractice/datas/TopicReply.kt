package com.example.apipractice.datas

import org.json.JSONObject

class TopicReply {

    var id = 0
    var topicId = 0
    var sideId = 0
    var userId = 0
    var content = ""

    lateinit var writer : User

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

            return tr
        }

    }
}