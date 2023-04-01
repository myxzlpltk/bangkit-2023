package com.dicoding.storyapp.models

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("settings")

class UserPreference @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    fun getUser(): Flow<UserModel?> {
        return dataStore.data.map { prefs ->
            if (prefs[STATE_KEY] == true) UserModel(
                prefs[USER_ID_KEY] ?: "",
                prefs[NAME_KEY] ?: "",
                prefs[TOKEN_KEY] ?: "",
            ) else null
        }
    }

    suspend fun login(userModel: UserModel) {
        dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userModel.userId
            prefs[NAME_KEY] = userModel.name
            prefs[TOKEN_KEY] = userModel.token
            prefs[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = ""
            prefs[NAME_KEY] = ""
            prefs[TOKEN_KEY] = ""
            prefs[STATE_KEY] = false
        }
    }

    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")
    }
}