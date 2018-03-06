package plus.marco.bus.Fragments

import android.app.Fragment
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceFragment
import android.util.Log
import android.view.*
import kotlinx.android.synthetic.main.fragment_settings.*
import plus.marco.bus.MainActivity
import plus.marco.bus.R

/**
 * Created by awataki on 3/3/18.
 */
class SettingsFragment:PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)

    }
}