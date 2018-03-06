package plus.marco.bus

import android.app.Fragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import plus.marco.bus.Fragments.GoFragment
import plus.marco.bus.Fragments.ReturnFragment
import plus.marco.bus.Fragments.SettingsFragment

//Settings
var go_from:Array<Boolean> = arrayOf(true,true,false)
var return_from:Array<Boolean> = arrayOf(true,true,true)

class MainActivity : AppCompatActivity() {

    //bottom menus
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.go -> {
                UpdatePreference()
                Fragment(R.id.Linear, GoFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.back -> {
                UpdatePreference()
                Fragment(R.id.Linear, ReturnFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu -> {
                Fragment(R.id.Linear, SettingsFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UpdatePreference()
        Fragment(R.id.Linear, GoFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    //ReplaceFragment(layoutid,Fragment())
    fun Fragment(id: Int, fragment:Fragment) {
        fragmentManager.beginTransaction().replace(id,fragment).commit()
    }

    //ReloadSettings
    fun UpdatePreference(){
        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        go_from[0] = pref.getBoolean("go_from0",true)
        go_from[1] = pref.getBoolean("go_from1",true)
        return_from[0] = pref.getBoolean("return_from0",true)
        return_from[1] = pref.getBoolean("return_from1",true)
        return_from[2] = pref.getBoolean("return_from2",true)
    }
}