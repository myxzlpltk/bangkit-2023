package com.dicoding.storyapp.presentation.ui.dashboard.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.data.preference.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val pref: UserPreference) : ViewModel() {

    val userLiveData = pref.getUser().mapNotNull { it }.asLiveData()

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}