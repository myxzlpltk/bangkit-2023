package com.example.githubuser.ui.settings

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getThemeSetting(): LiveData<Int> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(theme: Int) {
        viewModelScope.launch { pref.saveThemeSetting(theme) }
    }

    class Factory(private val pref: SettingPreferences) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(pref) as T
        }
    }
}