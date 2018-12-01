package plus.marco.bus.Fragments

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_time.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject
import plus.marco.bus.*
import plus.marco.bus.RecivedData.from
import plus.marco.bus.RecivedData.json


/**
 * Created by awataki on 2/28/18.
 */
class ReturnFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch(UI) {
            val webApi = WebApi(false)
            json = webApi.GetBusTime("https://marco.plus/api/returnFull").await() as JSONObject?
            Parser().Parse(Time, BusStop, Remaining)
            progressBar.visibility = View.GONE
        }
        FAB.setOnClickListener { Change() }
    }

    fun Change() {
        try {
            when (from) {
                0, 1 -> {
                    from = from!! + 1
                    var min = json!!.getJSONArray("from")[from!!].toString().toInt()
                    Parser().SetBusTime(min, Time, BusStop, Remaining, false)
                }
                2 -> {
                    from = 0
                    var min = json!!.getJSONArray("from")[from!!].toString().toInt()
                    Parser().SetBusTime(min, Time, BusStop, Remaining, false)
                }
                else -> {
                    "Error"
                }
            }
        } catch (e: Exception) {
            Log.e("Change", e.toString())
        }
    }
}