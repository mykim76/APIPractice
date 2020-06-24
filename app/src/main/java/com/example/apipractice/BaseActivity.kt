package com.example.apipractice

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this
    abstract fun setValues()
    abstract fun setupEvents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.let {
            setCustomActionBar() //BaseActivity를 상속받는 모든 액티비티는 자동으로 이 함수 실행
        }

    }
    fun  setCustomActionBar(){ //액션바 관련 세팅 변경

        // 액션바 커스텀 기능 활성화
        //supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        //커스텀 액션바 영역 확장 => 윗단 여백 제거
        supportActionBar?.setBackgroundDrawable(null) //기본 배경색 제거
        val parent = supportActionBar?.customView?.parent as Toolbar //실제 여백 제거
        parent.setContentInsetsAbsolute(0,0)
    }
}