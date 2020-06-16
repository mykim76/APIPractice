package com.example.apipractice.utils

import android.content.Context

class ContextUtil {
    
    companion object {
        
        val prefName = "APIPracticePref" // 파일 이름에 대응되는 개념:변수로 저장
        val USER_TOKEN = "USER_TOKEN" //저저ㅏㅇ할 항목들의 이름을 변수로 생성

        //항목에 데이터 저장(setter)
        fun setUserToken (context:Context, token : String)
        {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            pref.edit().putString(USER_TOKEN,token).apply() //항목에 데이터 저장(setter) or 불러오기 getter
        }

        //항목에 데이터 불러오기 getter
        fun getUserToken(context:Context):String{
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(USER_TOKEN, "")!!
        }
    }
}