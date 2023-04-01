package com.dicoding.storyapp.data.preference

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.storyapp.data.entity.User
import com.dicoding.storyapp.utils.Configuration
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(Configuration.DATASTORE_KEY)

@Singleton
class UserPreference @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    fun getUser(): Flow<User?> {
        return dataStore.data.map { prefs ->
            if (prefs[STATE_KEY] == true) User(
                prefs[USER_ID_KEY] ?: "",
                prefs[NAME_KEY] ?: "",
                prefs[TOKEN_KEY] ?: "",
            ) else null
        }
    }

    suspend fun login(user: User) {
        dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = user.userId
            prefs[NAME_KEY] = user.name
            prefs[TOKEN_KEY] = user.token
            prefs[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { prefs -> prefs.clear() }
    }

    suspend fun getToken(): String? {
        return dataStore.data.first()[TOKEN_KEY]
    }

    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")
    }
}