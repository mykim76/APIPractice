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

            return t
        }
    }
    var id=0
    var title = ""
    var imgUrl = ""
}