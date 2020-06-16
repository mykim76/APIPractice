package com.example.apipractice.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {
    
    //static에 대응되는 개념, 기능만 잘 되면 그만인 것들을 모아두는 영역
    companion object {
        val BASE_URL ="http://15.165.177.142"//호스트 주소 명시 => 가져다 사용

        //로그인 기능을 post로 요청하는 함수
        fun postRequestLogin(context: Context, email: String, pw: String, handler: JsonResponseHandler?){

            val client = OkHttpClient() //서버에 클라이언트로 동작해주는 변수

            val urlString = "${BASE_URL}/user" //어느 기능 주소로 가는지 host와 조합해서 명시

            //server에 전달할 데이터를 담는 과정(post - 폼데이터)
            val formData = FormBody.Builder()
                .add("email",email)
                .add("password",pw)
                .build()
            
            //서버에 요청할 모든 정보를 담는 request 변수 생성
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                //.header() //API에서 헤더를 요구하면 여기에 추가
                .build()

            //실제 호출(요청)
            client.newCall(request)
                .enqueue(object : Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        //연결 실패한 경우

                    }

                    override fun onResponse(call: Call, response: Response) {
                        //서버 연결 성공=>어떤 내용이든 응답은 받은 경우
                        val bodyString = response.body!!.string() //서버의 응답중 본문을 string으로 저장

                        //본문 string을 Json형태로 변환
                        val json = JSONObject(bodyString)
                        Log.d("JSON 응답:", json.toString())


                        handler?.onResponse(json) //JSON 파싱은 => 화면에서 진행하도록 처리(인터페이스 역활)
                        
                    }

                })
        }

        //중복체크를 get으로 요청하는 함수
        fun getRequestDuplicatedCheck(context: Context, type: String, input: String, handler: JsonResponseHandler?){
            val client = OkHttpClient()

            //GET 방식은 주소에 파라미터를 모두 적어줘야 함

            //val urlString = "${BASE_URL}/user" //어느 기능 주소로 가는지 host와 조합해서 명시
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder() //가공된 주소를 가지고 파마메타 첨주할 준비
                .addEncodedQueryParameter("type",type)
                .addEncodedQueryParameter("value",input)
                .build()

            val urlString = urlBuilder.toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                //.header() //필요시 첨부
                .build()

            //실제 호출(요청)
            client.newCall(request)
                .enqueue(object : Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        //연결 실패한 경우

                    }

                    override fun onResponse(call: Call, response: Response) {
                        //서버 연결 성공=>어떤 내용이든 응답은 받은 경우
                        val bodyString = response.body!!.string() //서버의 응답중 본문을 string으로 저장

                        //본문 string을 Json형태로 변환
                        val json = JSONObject(bodyString)
                        Log.d("JSON 응답:", json.toString())


                        handler?.onResponse(json) //JSON 파싱은 => 화면에서 진행하도록 처리(인터페이스 역활)

                    }

                })

        }

        //회원가입
        fun putRequestSingUp(context: Context, email: String, nickName : String, pw: String, handler: JsonResponseHandler?){

            val client = OkHttpClient() //서버에 클라이언트로 동작해주는 변수

            val urlString = "${BASE_URL}/user" //어느 기능 주소로 가는지 host와 조합해서 명시

            //server에 전달할 데이터를 담는 과정(post - 폼데이터)
            val formData = FormBody.Builder()
                .add("email",email)
                .add("password",pw)
                .add("nick_name",nickName)
                .build()

            //서버에 요청할 모든 정보를 담는 request 변수 생성
            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                //.header() //API에서 헤더를 요구하면 여기에 추가
                .build()

            //실제 호출(요청)
            client.newCall(request)
                .enqueue(object : Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        //연결 실패한 경우

                    }

                    override fun onResponse(call: Call, response: Response) {
                        //서버 연결 성공=>어떤 내용이든 응답은 받은 경우
                        val bodyString = response.body!!.string() //서버의 응답중 본문을 string으로 저장

                        //본문 string을 Json형태로 변환
                        val json = JSONObject(bodyString)
                        Log.d("JSON 응답:", json.toString())


                        handler?.onResponse(json) //JSON 파싱은 => 화면에서 진행하도록 처리(인터페이스 역활)

                    }

                })
        }

    }
    
    //서버통신 응답 내용을 액티비티에 전달하는 인터페이스
    interface JsonResponseHandler{
        fun onResponse(json: JSONObject)
    }
}