package plus.marco.bus

import android.os.AsyncTask
import android.util.Log
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import plus.marco.bus.Flags.Direction_flag
import plus.marco.bus.RecivedData.json


/**
 * Created by awataki on 3/2/18.
 */

class WebApi(direction: Boolean) {

    //ture go false return
    var tag = direction

    fun GetBusTime(url: String) = async(CommonPool) {
        val uri = url
        val client = OkHttpClient()
        try {
            val response = client
                    .newCall(Request.Builder()
                            .url(uri)
                            .build())
                    .execute()
                    .body()
                    .string()
            val json: JSONObject? = JSONObject(response)
            Log.e(tag.toString(), "respose is" + response)
            return@async json
        } catch (e: Exception) {

            Log.e(tag.toString(), e.toString())
            return@async null

        }
        Log.e("json", json.toString())


    }

}
