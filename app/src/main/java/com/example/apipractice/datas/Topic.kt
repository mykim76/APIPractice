package com.example.apipractice.datas

import org.json.JSONObject
import java.io.Serializable

class Topic : Serializable {

    companion object {

        fun getTopicFromJson(json: JSONObject) : Topic {
            val t = Topic()

            //t 에 들어갈 데이터를 => json에서 추출해서 대입
            t.id = json.getInt("id")
            t.title = json.getString("title")
            t.imgUrl = json.getString("img_url")
            t.mySideId = json.getInt("my_side_id")//내가 투표한 진영의 ID 투표 안했을 경우 -1

            //선택가능 진영 정보 파싱=>JsonArry파싱부터
            val sides = json.getJSONArray("sides")
            for(i in 0.. sides.length()-1)
            {
                val side = sides.getJSONObject(i)
//                side.getInt("id")
//                side.getInt("topic_id")
//                side.getString("title")
//                side.getInt("vote_count")
                val topicSide = TopicSide.getTopicsSideFromJason(side)//json=>TopicSide 객체화
                t.sideList.add(topicSide)//해당 주제 진영 배열에 추가


            }

            //주제의 모든 진영중 내 진영 id와 id 값이 같은 진영이 있는지 검사
            for(i in t.sideList.indices){
                if(t.sideList[i].id == t.mySideId){
                    //i번째 내가 선택한 진영을 찾음
                    t.mySelectedSideIndex=i;
                }
            }
            return t
        }
    }
    var id=0
    var title = ""
    var imgUrl = ""
    var mySideId = 0
    var mySelectedSideIndex = -1 //내가 선택한 진영이 첫번째 또는 두번째 안했는지 기억=>미선택:-1
    val sideList = ArrayList<TopicSide>()
}