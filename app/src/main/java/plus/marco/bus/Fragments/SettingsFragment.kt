package plus.marco.bus.Fragments

import android.os.Bundle
import android.preference.PreferenceFragment
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