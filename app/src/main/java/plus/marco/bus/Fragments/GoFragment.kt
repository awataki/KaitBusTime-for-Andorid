package plus.marco.bus.Fragments

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import plus.marco.bus.*
import kotlinx.android.synthetic.main.fragment_time.*

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
    }
}