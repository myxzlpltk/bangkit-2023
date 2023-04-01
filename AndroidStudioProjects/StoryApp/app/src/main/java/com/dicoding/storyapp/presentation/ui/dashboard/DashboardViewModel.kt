package com.dicoding.storyapp.presentation.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dicoding.storyapp.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(storyRepository: StoryRepository) : ViewModel() {

    val flow = storyRepository.getStories().cachedIn(viewModelScope)
}