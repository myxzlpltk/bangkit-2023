package com.dicoding.storyapp.presentation.ui.dashboard

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dicoding.storyapp.BaseViewModel
import com.dicoding.storyapp.data.preference.UserPreference
import com.dicoding.storyapp.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val pref: UserPreference,
    storyRepository: StoryRepository,
) : BaseViewModel() {

    val flow = storyRepository.getStories().cachedIn(viewModelScope)

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}