package plus.marco.bus

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import kotlin.math.min


/**
 * Created by awataki on 3/2/18.
 */

var json:JSONObject? = null
var from:Int? = null

class WebGet(f0:TextView,f1:TextView,f2:TextView,f3:Boolean): AsyncTask<String, Void, JSONObject>() {

    val fast = f0
    val remaining = f1
    val busstop = f2
    //ture go false return
    val bool = f3
    val fromarray= if (bool) go_from else return_from
    val tag = if (bool) "async:g" else "async:r"

    override fun doInBackground(vararg prams: String?): JSONObject? {

        val url = prams[0]
        val client = OkHttpClient()

        try {

            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val body = response.body().string()
            val json: JSONObject? = JSONObject(body)
            Log.i(tag, "respose is" + body)
            return json

        } catch (e: Exception) {

            Log.e(tag, e.toString())
            return null

        }

    }

    override fun onPostExecute(result: JSONObject?) {
        super.onPostExecute(result)
        json = result
        Parser()
    }

    fun Parser(){

        try {
            from = json!!.getJSONArray("fast")[1].toString().toInt()
            val min: Int =
                    if (fromarray[from!!]) {
                        //fastでいいとき
                        getjson(from!!)
                    } else {
                        //だめなとき
                        when (from) {
                            0 -> {
                                judge(1, 2)
                            }
                            1 -> {
                                judge(0, 2)
                            }
                            2 -> {
                                judge(0, 1)
                            }
                            else -> {
                                0
                            }
                        }
                    }

            SetBusTime(min,fast,busstop,remaining,bool)

        }catch (e:Exception) {
            busstop.text = "ServerError"
        }catch (e:JSONException){

            Log.e("tag",e.toString())
            busstop.text ="ParserError"
        }
    }


    fun getjson(number:Int):Int{
        try {
            from = number
            return json!!.getJSONArray("from")[number].toString().toInt()
        }catch (e:Exception){
            return 0
        }
    }

    fun judge (p0:Int,p1:Int):Int{
        if (fromarray[p0] == false && fromarray[p1] ==false) {
            return 0
        }
        else if (fromarray[p1] == false && fromarray[p0]) {
            return getjson(p0)
        }
        else if (fromarray[p0] == false && fromarray[p1]) {
            return getjson(p1)
        }
        else if (getjson(p1)==0) {
            return getjson(p0)
        }
        else if (getjson(p0)==0) {
            return getjson(p1)
        }
        else{
            return getjson(min(getjson(p0),getjson(p1)))
        }
    }
}

fun SetBusTime (min:Int,fast:TextView,busstop:TextView,remaining:TextView,bool:Boolean){
    val h:String = (min/60).toString()
    var m:String = (min%60).toString()
    var remain = min-((SimpleDateFormat("HH").format(System.currentTimeMillis())).toInt()*60+SimpleDateFormat("mm").format(System.currentTimeMillis()).toInt())

    fast.text =
            if (min==0)
                "nothing"
            else if (m.toInt()<10)
                h+":0"+m
            else
                h+":"+m

    busstop.text =
            if (min == 0){
                "empty"
            } else {
                when (bool) {
                    true -> {
                        when (from) {
                            0 -> "バスセンター"
                            1 -> "駅前乗り場"
                            else -> "error"
                        }
                    }
                    false -> {
                        when (from) {
                            0 -> "学内"
                            1 -> "路線"
                            2 -> "シャトルバス"
                            else -> "error"
                        }
                    }

                }
            }

    remaining.text =
            if (min == 0){
                "empty"
            } else {
                "発車まであと" +
                        if (remain > 60)
                            (remain / 60).toString() + "時間" + (remain % 60).toString() + "分"
                        else
                            remain.toString() + "分"
            }
}
