package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.ui.login.mJsonString
import kotlinx.android.synthetic.main.content_usage_status2.*
//import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

var errorMessage_wm = "세탁기 현황 정보를 불러올 수 없습니다."
var arrWMlist = mutableListOf<Array<Int>>()    //사용자 정보에 해당하는 기숙사의 세탁기들 리스트
var runningWM = mutableListOf<Int>()
var emptyWM = mutableListOf<Int>()
var buttonList = mutableListOf<Button>()
var usingList = mutableListOf<Button>()
var userNum = ""

class UsageStatusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usage_status2)
        setSupportActionBar(findViewById(R.id.toolbar))

        var user_num = intent.getStringExtra("user_num")
        userNum = user_num.toString()
        var id = intent.getStringExtra("id")
        var pw = intent.getStringExtra("pw")
        var name = intent.getStringExtra("name")
        var dorm_num = intent.getStringExtra("dorm_num")
        var phone_num = intent.getStringExtra("phone_num")
        var using_num = intent.getStringExtra("using_num")

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val MypageActivity = Intent(this, MypageActivity::class.java)
            MypageActivity.putExtra("user_num",user_num)
            MypageActivity.putExtra("id", id)
            MypageActivity.putExtra("pw", pw)
            MypageActivity.putExtra("name", name)
            MypageActivity.putExtra("dorm_num", dorm_num)
            MypageActivity.putExtra("phone_num", phone_num)
            MypageActivity.putExtra("using_num", using_num)
            startActivity(MypageActivity)
        }
        val task = readData()
        task.execute("http://192.168.0.17/getjson_readWM.php",dorm_num)

    }

    fun setButton(){
        var WM_num = ""
        for (i in buttonList){

            //빈 세탁기 이미지 삽입
            i.setBackgroundResource(R.drawable.img_wm_empty)

            i.setOnClickListener{
                WM_num = i.getText().toString()
                val RegisterActivity = Intent(this, RegisterActivity::class.java)
                RegisterActivity.putExtra("user_num",userNum)
                RegisterActivity.putExtra("WM_num", WM_num)
                startActivity(RegisterActivity)
            }
        }

        for (i in usingList){
            //사용중인 세탁기 이미지 삽입
            /* 코드 보충 필요 : if (내가쓰는 세탁기) */
                //i.setBackgroundResource(R.drawable.img_wm_myuse)
            /* 코드 보충 필요 : else (남이 쓰는 세탁기 세탁기) */
            i.setBackgroundResource(R.drawable.img_wm_otheruse)
        }
    }

    fun setWM(arrWMlist: MutableList<Array<Int>>){
        WM1.visibility = View.GONE
        WM2.visibility = View.GONE
        WM3.visibility = View.GONE
        WM4.visibility = View.GONE
        WM5.visibility = View.GONE
        WM6.visibility = View.GONE
        WM7.visibility = View.GONE
        WM8.visibility = View.GONE
        WM9.visibility = View.GONE
        WM10.visibility = View.GONE
        WM11.visibility = View.GONE
        WM12.visibility = View.GONE

        for(i in arrWMlist){
            var WM_num = i[0]
            var row = i[1]
            var column = i[2]
            var running = i[3]
            if(row==1 && column==1){
                WM1.visibility = View.VISIBLE
                WM1.text = WM_num.toString()
                if(running==0){
                    emptyWM.add(1)
                    buttonList.add(WM1)
                }
                else if (running==1) {
                    runningWM.add(1)
                    usingList.add(WM1)
                }
            }
            if(row==1 && column==2){
                WM2.visibility = View.VISIBLE
                WM2.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(2)
                    buttonList.add(WM2)
                }
                else if (running==1) {
                    runningWM.add(2)
                    usingList.add(WM2)
                }
            }
            if(row==1 && column==3){
                WM3.visibility = View.VISIBLE
                WM3.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(3)
                    buttonList.add(WM3)
                }
                else if (running==1) {
                    runningWM.add(3)
                    usingList.add(WM3)
                }
            }
            if(row==2 && column==1){
                WM4.visibility = View.VISIBLE
                WM4.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(4)
                    buttonList.add(WM4)
                }
                else if (running==1) {
                    runningWM.add(4)
                    usingList.add(WM4)
                }
            }
            if(row==2 && column==2){
                WM5.visibility = View.VISIBLE
                WM5.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(5)
                    buttonList.add(WM5)
                }
                else if (running==1) {
                    runningWM.add(5)
                    usingList.add(WM5)
                }
            }
            if(row==2 && column==3){
                WM6.visibility = View.VISIBLE
                WM6.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(6)
                    buttonList.add(WM6)
                }
                else if (running==1) {
                    runningWM.add(6)
                    usingList.add(WM6)
                }
            }
            if(row==3 && column==1){
                WM7.visibility = View.VISIBLE
                WM7.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(7)
                    buttonList.add(WM7)
                }
                else if (running==1) {
                    runningWM.add(7)
                    usingList.add(WM7)
                }
            }
            if(row==3 && column==2){
                WM8.visibility = View.VISIBLE
                WM8.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(8)
                    buttonList.add(WM8)
                }
                else if (running==1) {
                    runningWM.add(8)
                    usingList.add(WM8)
                }
            }
            if(row==3 && column==3){
                WM9.visibility = View.VISIBLE
                WM9.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(9)
                    buttonList.add(WM9)
                }
                else if (running==1) {
                    runningWM.add(9)
                    usingList.add(WM9)
                }
            }
            if(row==4 && column==1){
                WM10.visibility = View.VISIBLE
                WM10.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(10)
                    buttonList.add(WM10)
                }
                else if (running==1) {
                    runningWM.add(10)
                    usingList.add(WM10)
                }
            }
            if(row==4 && column==2){
                WM11.visibility = View.VISIBLE
                WM11.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(11)
                    buttonList.add(WM11)
                }
                else if (running==1) {
                    runningWM.add(11)
                    usingList.add(WM11)
                }
            }
            if(row==4 && column==3){
                WM12.visibility = View.VISIBLE
                WM12.text = WM_num.toString()
                if(running==0) {
                    emptyWM.add(12)
                    buttonList.add(WM12)
                }
                else if (running==1) {
                    runningWM.add(12)
                    usingList.add(WM12)
                }
            }
            setButton()
        }
    }

    private inner class readData : AsyncTask<String?, Void?, String?>() {

        @SuppressLint("SetTextI18n")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result == null) {
                Toast.makeText(
                    applicationContext,
                    errorMessage_wm,
                    Toast.LENGTH_LONG
                ).show()

            } else {
                mJsonString = result
                val TAG_JSON = "webnautes"
                val TAG_WM_num = "WM_num"
                val TAG_row = "position_row"
                val TAG_column = "position_column"
                val TAG_running = "running"
                try {
                    val jsonObject = JSONObject(result)
                    val jsonArray: JSONArray = jsonObject.getJSONArray(TAG_JSON)
                    for (i in 0 until jsonArray.length()) {
                        val item: JSONObject = jsonArray.getJSONObject(i)
                        val WM_num: String = item.getString(TAG_WM_num)
                        val row: String = item.getString(TAG_row)
                        val column: String = item.getString(TAG_column)
                        val running: String = item.getString(TAG_running)
                        var arrWM = arrayOf(WM_num.toInt(),row.toInt(),column.toInt(),running.toInt())    //한개의 세탁기에 대한 정보 저장하는 리스트
                        arrWMlist.add(arrWM)
                    }
                    setWM(arrWMlist)
                } catch (e: JSONException) {
                    Toast.makeText(
                        applicationContext,
                        errorMessage_wm,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        override fun doInBackground(vararg params: String?): String? {
            val serverURL = params[0]
            val dorm = params[1]
            var dorm_num = 0
            if(dorm == "계영원")
                dorm_num=1
            val postParameters: String = "dorm_num=$dorm_num"

            return try {
                val url = URL(serverURL)
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

                httpURLConnection.readTimeout = 5000
                httpURLConnection.connectTimeout = 5000
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.connect()

                val outputStream: OutputStream = httpURLConnection.outputStream
                if (postParameters != null) {
                    outputStream.write(postParameters.toByteArray(charset("UTF-8")))
                }
                outputStream.flush()
                outputStream.close()

                val responseStatusCode: Int = httpURLConnection.responseCode

                val inputStream: InputStream
                inputStream = if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    httpURLConnection.inputStream
                } else {
                    httpURLConnection.errorStream
                }


                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)

                val sb = StringBuilder()
                var line: String? = null

                while (bufferedReader.readLine().also({ line = it }) != null) {
                    sb.append(line)
                }

                bufferedReader.close();

                return sb.toString();

            }catch (e: Exception) {
                //errorString = e.toString()
                null
            }
        }
    }
}