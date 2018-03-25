package plus.marco.bus

import android.util.Log
import android.widget.TextView
import org.json.JSONException
import plus.marco.bus.Flags.Direction_flag
import plus.marco.bus.Flags.go_from
import plus.marco.bus.Flags.return_from
import plus.marco.bus.RecivedData.from
import plus.marco.bus.RecivedData.json
import java.text.SimpleDateFormat
import kotlin.math.min

/**
 * Created by awataki on 3/14/18.
 */
open class Parser{

    fun SetBusTime (min:Int, fast: TextView, busstop: TextView, remaining: TextView, bool:Boolean){
        val h:String = (min/60).toString()
        var m:String = (min%60).toString()
        var remain = min-((SimpleDateFormat("HH").format(System.currentTimeMillis())).toInt()*60+ SimpleDateFormat("mm").format(System.currentTimeMillis()).toInt())

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


    fun getjson(number:Int):Int{
        try {
            from = number
            return json!!.getJSONArray("from")[number].toString().toInt()
        }catch (e:Exception){
            return 0
        }
    }

    fun judge (p0:Int,p1:Int,fromarray:Array<Boolean> = if (Direction_flag == true) go_from else return_from):Int{
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
    fun Parse(fast: TextView,busstop: TextView,remaining: TextView,fromarray: Array<Boolean> = if (Flags.Direction_flag == true) Flags.go_from else Flags.return_from){

        try {
            from = json?.getJSONArray("fast")?.get(1).toString().toInt()
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

            SetBusTime(min, fast, busstop, remaining, Direction_flag)

        }catch (e:Exception) {
            busstop.text = "ServerError"
            Log.e("parcer",e.toString())
        }catch (e: JSONException){

            Log.e("tag",e.toString())
            busstop.text ="ParserError"
        }
    }
}
