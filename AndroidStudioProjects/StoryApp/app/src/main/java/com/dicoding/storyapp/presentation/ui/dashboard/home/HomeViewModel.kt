package com.dicoding.storyapp.presentation.ui.dashboard.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dicoding.storyapp.BaseViewModel
import com.dicoding.storyapp.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    storyRepository: StoryRepository,
) : BaseViewModel() {

    val stories = storyRepository.getPagingData().cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        Timber.d("HomeViewModel onCleared")
    }
}