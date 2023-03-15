package com.example.mysettingpreference

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var age: String
    private lateinit var phone: String
    private lateinit var love: String

    private lateinit var namePreference: EditTextPreference
    private lateinit var emailPreference: EditTextPreference
    private lateinit var agePreference: EditTextPreference
    private lateinit var phonePreference: EditTextPreference
    private lateinit var isLoveMuPreference: CheckBoxPreference

    companion object {
        private const val DEFAULT_VALUE = "Tidak Ada"
    }

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)

        init()
        setSummaries()
    }

    private fun init() {
        name = getString(R.string.key_name)
        email = getString(R.string.key_email)
        age = getString(R.string.key_age)
        phone = getString(R.string.key_phone)
        love = getString(R.string.key_love)

        namePreference = findPreference<EditTextPreference>(name) as EditTextPreference
        emailPreference = findPreference<EditTextPreference>(email) as EditTextPreference
        agePreference = findPreference<EditTextPreference>(age) as EditTextPreference
        phonePreference = findPreference<EditTextPreference>(phone) as EditTextPreference
        isLoveMuPreference = findPreference<CheckBoxPreference>(love) as CheckBoxPreference
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        namePreference.summary = sh?.getString(name, DEFAULT_VALUE)
        emailPreference.summary = sh?.getString(email, DEFAULT_VALUE)
        agePreference.summary = sh?.getString(age, DEFAULT_VALUE)
        phonePreference.summary = sh?.getString(phone, DEFAULT_VALUE)
        isLoveMuPreference.isChecked = sh?.getBoolean(love, false) == true
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == name) {
            namePreference.summary = sharedPreferences.getString(name, DEFAULT_VALUE)
        }
        if (key == email) {
            emailPreference.summary = sharedPreferences.getString(email, DEFAULT_VALUE)
        }
        if (key == age) {
            agePreference.summary = sharedPreferences.getString(age, DEFAULT_VALUE)
        }
        if (key == phone) {
            phonePreference.summary = sharedPreferences.getString(phone, DEFAULT_VALUE)
        }
        if (key == love) {
            isLoveMuPreference.isChecked = sharedPreferences.getBoolean(love, false)
        }
    }

}