package plus.marco.bus.Fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_time.*
import plus.marco.bus.R
import plus.marco.bus.WebGet

/**
 * Created by awataki on 2/28/18.
 */
class ReturnFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       WebGet(Time,Remaining,BusStop,false).execute("http://marco.plus/api/returnFull")
       }
}