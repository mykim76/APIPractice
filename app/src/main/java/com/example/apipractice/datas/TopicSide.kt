package com.example.apipractice.datas

import org.json.JSONObject

class TopicSide {

    var id = 0
    var topicId = 0
    var title = ""
    var vote_count = 0

    companion object {
        //json 덩어리 input => 내용이 모두 적힌 TopicSide 객체 리턴
        fun getTopicsSideFromJason(json:JSONObject):TopicSide{

            val ts = TopicSide()
            ts.id = json.getInt("id")
            ts.topicId = json.getInt("topic_id")
            ts.title = json.getString("title")
            ts.vote_count = json.getInt("vote_count")
            return ts
        }

    }
}