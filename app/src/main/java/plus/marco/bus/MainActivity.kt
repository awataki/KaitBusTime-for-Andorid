package plus.marco.bus

import android.app.Fragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import plus.marco.bus.Flags.Direction_flag
import plus.marco.bus.Flags.go_from
import plus.marco.bus.Flags.return_from
import plus.marco.bus.Fragments.GoFragment
import plus.marco.bus.Fragments.ReturnFragment
import plus.marco.bus.Fragments.SettingsFragment

//Settings

class MainActivity : AppCompatActivity() {

    //bottom menus
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.go -> {
                updatePref()
                replaceFragment(R.id.Linear, GoFragment())
                Direction_flag = true
                return@OnNavigationItemSelectedListener true
            }
            R.id.back -> {
                updatePref()
                replaceFragment(R.id.Linear, ReturnFragment())
                Direction_flag = false
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu -> {
                replaceFragment(R.id.Linear, SettingsFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updatePref()
        replaceFragment(R.id.Linear, GoFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    //ReplaceFragment(layoutId,Fragment())
    private fun replaceFragment(id: Int, fragment:Fragment) {
        fragmentManager
                .beginTransaction()
                .replace(id,fragment)
                .commit()
    }

    //ReloadSettings
    private fun updatePref(){
        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        go_from[0] = pref.getBoolean("go_from0",true)
        go_from[1] = pref.getBoolean("go_from1",true)
        return_from[0] = pref.getBoolean("return_from0",true)
        return_from[1] = pref.getBoolean("return_from1",true)
        return_from[2] = pref.getBoolean("return_from2",true)
    }
}