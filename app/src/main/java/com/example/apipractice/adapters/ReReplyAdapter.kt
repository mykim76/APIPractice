package com.example.apipractice.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.apipractice.R
import com.example.apipractice.ViewReplyDetailActivity
import com.example.apipractice.datas.TopicReply
import com.example.apipractice.utils.ServerUtil
import org.json.JSONObject
import java.text.SimpleDateFormat

class ReReplyAdapter(context: Context, resId:Int, list: List<TopicReply>):
    ArrayAdapter<TopicReply>(context, resId,list)  {

    val mContext = context
    val mList = list
    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        if(tempRow==null){
            tempRow = inf.inflate(R.layout.topic_re_reply_list_item, null)
        }

        var row = tempRow!!

        val txtWriter = row.findViewById<TextView>(R.id.txtWriter)
        val txtContent = row.findViewById<TextView>(R.id.txtContent)

        val btnLikeCount = row.findViewById<Button>(R.id.btnLikeCount)
        val btnDislikeCount = row.findViewById<Button>(R.id.btnDislikeCount)

        val txtSelectedSide = row.findViewById<TextView>(R.id.txtSelectedSide)

        val data = mList[position]


        txtWriter.text = "${data.writer.nickName}"
        txtContent.text = "${data.content}"

        btnLikeCount.text = "좋아요: ${data.likeCount}개"
        btnDislikeCount.text = "싫어요: ${data.dislikeCount}개"

        //나의 좋아요:빨강/싫어요:회색 여부를 표시
        if(data.isMyLike){
            btnLikeCount.setBackgroundResource(R.drawable.red_border_box)
            btnDislikeCount.setBackgroundResource(R.drawable.grey_border_box)

            btnLikeCount.setTextColor(mContext.resources.getColor(R.color.red))
            btnDislikeCount.setTextColor(mContext.resources.getColor(R.color.grey))
        }
        else if(data.isMyDislike){
            btnLikeCount.setBackgroundResource(R.drawable.grey_border_box)
            btnDislikeCount.setBackgroundResource(R.drawable.blue_border_box)

            btnLikeCount.setTextColor(mContext.resources.getColor(R.color.grey))
            btnDislikeCount.setTextColor(mContext.resources.getColor(R.color.blue))
        }
        else{
            btnLikeCount.setBackgroundResource(R.drawable.grey_border_box)
            btnDislikeCount.setBackgroundResource(R.drawable.grey_border_box)

            btnLikeCount.setTextColor(mContext.resources.getColor(R.color.grey))
            btnDislikeCount.setTextColor(mContext.resources.getColor(R.color.grey))
        }


        //선택진영 정보
        txtSelectedSide.text = "(${data.selectedSide.title})"

        // 좋아요/싫어요 클릭 이벤트
        val likeOrDislikeEvent = View.OnClickListener {
            //좋아요: is_like - true
            //싫어요: is_like - false
            val isLike = it.id == R.id.btnLikeCount //클릭한 버튼이 btnLikeCount 인지 btnDislikeCount인지 체크

            ServerUtil.postRequestReplyLikeOrDislike(mContext,data.id, isLike, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

                    //data변수 내부 값중 좋아요/싫어요 갯수 변경
                    data.likeCount = reply.getInt("like_count")
                    data.dislikeCount = reply.getInt("dislike_count")

                    //data변수 내부 값중 나의 좋아요/ 싫어요 변경
                    data.isMyDislike = reply.getBoolean("my_dislike")
                    data.isMyLike = reply.getBoolean("my_like")


                    Handler(Looper.getMainLooper()).post{
                        notifyDataSetChanged()
                    }



                }

            })

        }

        btnLikeCount.setOnClickListener(likeOrDislikeEvent)
        btnDislikeCount.setOnClickListener(likeOrDislikeEvent)

        return row
    }
}