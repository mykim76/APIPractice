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

            //선택가능 진영 정보 파싱=>JsonArry파싱부터
            val sides = json.getJSONArray("sides")
            for(i in 0.. sides.length()-1)
            {
                val side = sides.getJSONObject(i)
//                side.getInt("id")
//                side.getInt("topic_id")
//                side.getString("title")
//                side.getInt("vote_count")

            }

            return t
        }
    }
    var id=0
    var title = ""
    var imgUrl = ""
}