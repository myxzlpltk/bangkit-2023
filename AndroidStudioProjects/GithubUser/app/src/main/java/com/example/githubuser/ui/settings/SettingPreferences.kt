package com.example.githubuser.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(context: Context): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                SettingPreferences(context).also { INSTANCE = it }
            }
        }
    }

    private val dataStore = context.dataStore
    private val themeKey = intPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Int> {
        return dataStore.data.map { preferences -> preferences[themeKey] ?: 0 }
    }

    suspend fun saveThemeSetting(theme: Int) {
        dataStore.edit { preferences -> preferences[themeKey] = theme }
    }
}