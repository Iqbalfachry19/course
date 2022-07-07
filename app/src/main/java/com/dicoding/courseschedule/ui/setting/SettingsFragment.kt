package com.dicoding.courseschedule.ui.setting

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.*
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NOTIFICATION_CHANNEL_ID
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val prefTheme =
            findPreference<ListPreference>(getString(R.string.pref_key_dark))
        prefTheme?.setOnPreferenceChangeListener { _, newValue ->
            when (newValue) {
                getString(R.string.pref_dark_on) -> updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                getString(R.string.pref_dark_off) -> updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                else -> updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

        }
        val prefNotification =
            findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotification?.setOnPreferenceChangeListener { _, newValue ->
           val  dailyReminder =  DailyReminder()
            when (newValue) {
                true -> {
                    dailyReminder.setDailyReminder(requireContext())
                }
                false -> {
                    dailyReminder.cancelAlarm(requireContext())
                }
            }
            true
        }
    }
    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}