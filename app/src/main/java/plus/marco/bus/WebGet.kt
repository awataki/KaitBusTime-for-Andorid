package plus.marco.bus

import android.os.AsyncTask
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import plus.marco.bus.Flags.Direction_flag
import plus.marco.bus.RecivedData.json


/**
 * Created by awataki on 3/2/18.
 */

class WebGet: AsyncTask<String, Void, JSONObject>() {

    //ture go false return
    val tag = if (Direction_flag) "async:g" else "async:r"

    override fun doInBackground(vararg prams: String?): JSONObject? {

        val url = prams[0]
        val client = OkHttpClient()

        try {

            val response = client.newCall(Request.Builder().url(url).build()).execute().body().string()
            val json: JSONObject? = JSONObject(response)
            Log.i(tag, "respose is" + response)
            return json

        } catch (e: Exception) {

            Log.e(tag, e.toString())
            return null

        }

    }

    override fun onPostExecute(result: JSONObject?) {
        super.onPostExecute(result)
        json = result
        Log.e("json", json.toString())
    }

}
