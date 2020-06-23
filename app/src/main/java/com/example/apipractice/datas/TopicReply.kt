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
    //like_count / dislike_count/reply_count
    var likeCount = 0
    var dislikeCount = 0
    var replyCount = 0

    lateinit var selectedSide : TopicSide //의견이 어떤 진영을 옹호하는지

    lateinit var writer : User
    val createdAt = Calendar.getInstance() //작성 일시를 시간형태로 저장(기본:현재시간)

    var isMyLike = false
    var isMyDislike = false

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
            
            val myPhoneTimeZone = tr.createdAt.timeZone //어느지역 시간대인지 따서 저장

            val timeOffset = myPhoneTimeZone.rawOffset / 1000/60/60 //몇시간 차이가 나는지 계산 rawOffset:밀리초단위 =>시간으로 변경
            tr.createdAt.add(Calendar.HOUR, timeOffset) //계시글 작성시간을 timeOffset만큼 변경

            //좋아요/싫어요/답글  목록 화면에 반영
            //like_count / dislike_count/reply_count
            tr.likeCount = json.getInt("like_count")
            tr.dislikeCount = json.getInt("dislike_count")
            tr.replyCount = json.getInt("reply_count")

            //선택진영정보 파싱
            tr.selectedSide = TopicSide.getTopicsSideFromJason(json.getJSONObject("selected_side"))

            tr.isMyLike = json.getBoolean("my_like")
            tr.isMyDislike = json.getBoolean("my_dislike")

            return tr
        }

    }
}