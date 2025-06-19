package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.samsung_coursework.MainActivity
import com.example.samsung_coursework.R
import com.example.samsung_coursework.ui.view_model.ProfileViewModel

class FragmentSettings : PreferenceFragmentCompat() {
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref, rootKey)

        val cityPref: ListPreference? = findPreference("primary_city")
        cityPref?.apply {
            summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
            icon = null
            isIconSpaceReserved = false
        }

        val languagePref: ListPreference? = findPreference("language")
        languagePref?.apply {
            summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
            icon = null
            isIconSpaceReserved = false

            setOnPreferenceChangeListener { pref, newValue ->
                (activity as? MainActivity)?.reloadApp()
                true
            }
        }

        val nicknamePref: EditTextPreference? = findPreference("nickname")
        nicknamePref?.apply {
            icon = null
            isIconSpaceReserved = false
        }

        nicknamePref?.setOnPreferenceChangeListener { pref, newValue ->
            val newNickname = newValue.toString()
            if (newNickname.isNotBlank()) {
                viewModel.changeNickname(newNickname)
                true
            } else {
                false
            }
        }

        val stayLoggedIn: SwitchPreferenceCompat? = findPreference("stay_logged_in")
        stayLoggedIn?.apply {
            icon = null
            isIconSpaceReserved = false
        }

    }

}