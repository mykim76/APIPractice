package com.example.apipractice.utils

import java.util.*

class TimeUtil {
    companion object{

        //
        fun getTimeAgoFromCalendar(datetime:Calendar):String{

            var agoStr = ""

            //현재 시간을 Calendar 형태로 추출
            val now = Calendar.getInstance()
            // 현재 시간 - 넘어오는 시간=>몇ms 차이가 나는지?
            //val msDiff = now.date
            return agoStr
        }
    }
}