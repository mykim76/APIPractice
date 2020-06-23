package com.example.apipractice.adapters

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.apipractice.R
import com.example.apipractice.datas.Topic
import com.example.apipractice.datas.TopicReply
import com.example.apipractice.utils.ServerUtil
import okhttp3.internal.http2.Http2Reader
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.logging.Handler

class ReplyAdapter(context: Context, resId:Int, list: List<TopicReply>):
    ArrayAdapter<TopicReply>(context, resId,list)  {
    val mContext = context
    val mList = list
    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        tempRow?.let{
            //null이 아닐때 실행할 함수들

        }.let{
            //null일때 실행할 함수
            tempRow = inf.inflate(R.layout.topic_reply_list_item, null)
        }

        var row = tempRow!!

        val txtWriter = row.findViewById<TextView>(R.id.txtWriter)
        val txtContent = row.findViewById<TextView>(R.id.txtContent)
        val txtWriteTime = row.findViewById<TextView>(R.id.txtWriteTime)

        //
        val btnReplyCount = row.findViewById<Button>(R.id.btnReplyCount)
        val btnLikeCount = row.findViewById<Button>(R.id.btnLikeCount)
        val btnDislikeCount = row.findViewById<Button>(R.id.btnDislikeCount)

        val txtSelectedSide = row.findViewById<TextView>(R.id.txtSelectedSide)

        val data = mList[position]


        txtWriter.text = "${data.writer.nickName}"
        txtContent.text = "${data.content}"

        btnReplyCount.text = "답글: ${data.replyCount}개"
        btnLikeCount.text = "좋아요: ${data.likeCount}개"
        btnDislikeCount.text = "싫어요: ${data.dislikeCount}개"

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

                     //리스트뷰에 뿌려지는 데이터에 내용 변경 =>  odifyDataSetChanged 필요
                     //어댑터변수.notify~ 실행. 그러나 현재 어댑터변수가 없으니 어찌할 것인가
                     
                     //runOnUriThread필요
                     //Handler(Looper.getMainLooper()).post {
//                     android.os.Handler(Looper.getMainLooper()).post {
//                         notifyDataSetChanged() //어댑터내부에서 직접 새로고침 가능 => runOnUriThread필요
//                     }




                 }

             })

         }

        btnLikeCount.setOnClickListener(likeOrDislikeEvent)
        btnDislikeCount.setOnClickListener(likeOrDislikeEvent)

        val sdf = SimpleDateFormat("M월 d일 a h시 m분")

        txtWriteTime.text = sdf.format(data.createdAt.time) //?월?일 오전/오후 ?시 ?분 출력

        return row
    }
}