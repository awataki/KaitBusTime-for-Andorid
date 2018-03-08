package plus.marco.bus.Fragments

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_time.*
import plus.marco.bus.*

/**
 * Created by awataki on 2/28/18.
 */
class GoFragment :Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WebGet(Time,Remaining,BusStop,true).execute("http://marco.plus/api/goFull")
        FAB.setOnClickListener{Change()}
    }
    fun Change(){
        try {
            when(from){
                0 -> {
                    from = 1
                    var min = json!!.getJSONArray("from")[1].toString().toInt()
                    SetBusTime(min,Time,BusStop,Remaining,true)
                }
                1 -> {
                    from = 0
                    var min = json!!.getJSONArray("from")[0].toString().toInt()
                    SetBusTime(min,Time,BusStop,Remaining,true)
                }
                else -> {
                    "Error"
                }
            }
        }catch (e : Exception){
            Log.e("Change",e.toString())
        }
    }
}