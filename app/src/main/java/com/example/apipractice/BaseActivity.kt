package com.example.apipractice

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this
    
    lateinit var txtActivityTitle : TextView //제목을 나타내는 텍스트뷰
    lateinit var imgLogo : ImageView // 제목이 없을 때 보여줄 이미지
    lateinit var imgNotification : ImageView //알림 목록에 들어가는 버튼

    lateinit var notiFramlayout : FrameLayout
    abstract fun setValues()
    abstract fun setupEvents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.let {
            setCustomActionBar() //BaseActivity를 상속받는 모든 액티비티는 자동으로 이 함수 실행
        }

    }

    override fun setTitle(title: CharSequence?) { //각 화면의 setTitle 기본 기능=> 커스텀 액션바에게 반영하도록 오버라이딩
        super.setTitle(title)
        supportActionBar?.let { //액션 바가 있을 때만 실행
            //로고는 숨기고, 글씨 보이게
            imgLogo.visibility = View.GONE
            txtActivityTitle.visibility = View.VISIBLE

            txtActivityTitle.text = title
        }

    }
    fun  setCustomActionBar(){ //액션바 관련 세팅 변경

        // 액션바 커스텀 기능 활성화
        //supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        //커스텀 액션바 영역 확장 => 윗단 여백 제거
        supportActionBar!!.setBackgroundDrawable(null) //기본 배경색 제거
        val parent = supportActionBar!!.customView?.parent as Toolbar //실제 여백 제거
        parent.setContentInsetsAbsolute(0,0)

        //XML 에 있는 뷰들을 사용할 수 있도록 연결
        txtActivityTitle = supportActionBar!!.customView.findViewById(R.id.txtActivityTitle)
        imgLogo = supportActionBar!!.customView.findViewById(R.id.imgLogo)
        imgNotification = supportActionBar!!.customView.findViewById(R.id.imgNotification)

        notiFramlayout = supportActionBar!!.customView.findViewById(R.id.notiFrameLayout)

        //알림 버튼을 눌리면 어느 화면에서건 알림 화면으로 이동
        imgNotification.setOnClickListener {
            val myIntent = Intent(mContext, NotificationListActivity::class.java)
            startActivity(myIntent)
        }
    }
}