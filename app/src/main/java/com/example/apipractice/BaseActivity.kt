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
import com.example.apipractice.utils.ContextUtil
import com.example.apipractice.utils.ServerUtil
import org.json.JSONObject

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this
    
    lateinit var txtActivityTitle : TextView //제목을 나타내는 텍스트뷰
    lateinit var imgLogo : ImageView // 제목이 없을 때 보여줄 이미지
    lateinit var imgNotification : ImageView //알림 목록에 들어가는 버튼

    lateinit var notiFramlayout : FrameLayout
    lateinit var txtUnreadNotiCount : TextView
    abstract fun setValues()
    abstract fun setupEvents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.let {
            setCustomActionBar() //BaseActivity를 상속받는 모든 액티비티는 자동으로 이 함수 실행
        }

    }

    override fun onResume() {// 모든 화면에서 알림 갯수를 받아와서 표시 // 화면에 돌아올 때마다 실행
        super.onResume()

        if(ContextUtil.getUserToken(mContext) != "")
        {
            ServerUtil.getRequestNotification(mContext, false, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    supportActionBar?.let {

                        val data = json.getJSONObject("data")
                        val unreadNotiCount = data.getInt("unread_noty_count")

                        runOnUiThread{
                            if(unreadNotiCount>0) {
                                //빨강 동그라미 표시 + 몇갠지 글자도 표시
                                txtUnreadNotiCount.visibility = View.VISIBLE
                                txtUnreadNotiCount.text = unreadNotiCount.toString()
                            }
                            else{
                                //
                                txtUnreadNotiCount.visibility = View.GONE
                                txtUnreadNotiCount.text = unreadNotiCount.toString()

                            }
                        }
                    }

                }

            })
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
        txtUnreadNotiCount = supportActionBar!!.customView.findViewById(R.id.txtUnreadNotiCount)

        //알림 버튼을 눌리면 어느 화면에서건 알림 화면으로 이동
        imgNotification.setOnClickListener {
            val myIntent = Intent(mContext, NotificationListActivity::class.java)
            startActivity(myIntent)
        }
    }
}