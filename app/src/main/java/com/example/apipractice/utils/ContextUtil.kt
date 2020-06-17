package com.example.apipractice.utils

import android.content.Context

class ContextUtil {
    
    companion object {
        
        val prefName = "APIPracticePref" // 파일 이름에 대응되는 개념:변수로 저장
        val USER_TOKEN = "USER_TOKEN" //저저ㅏㅇ할 항목들의 이름을 변수로 생성
        val AUTO_LOGIN = "AUTO_LOGIN"

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

        fun setAutoLogin(context:Context, autoLogin : Boolean){

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE) //메모장 열기
            pref.edit().putBoolean(AUTO_LOGIN, autoLogin).apply()

        }

        fun isAutoLogin(context: Context):Boolean{
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE) //메모장 열기
            return pref.getBoolean(AUTO_LOGIN,false)

        }
    }
}