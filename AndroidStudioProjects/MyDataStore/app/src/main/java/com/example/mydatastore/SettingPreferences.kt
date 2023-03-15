package com.example.mydatastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null
        private val THEME_KEY = booleanPreferencesKey("theme_setting")

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                return SettingPreferences(dataStore).apply { INSTANCE = this }
            }
        }
    }



    fun getThemeSetting(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: false
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) = dataStore.edit { preferences ->
        preferences[THEME_KEY] = isDarkModeActive
    }
}