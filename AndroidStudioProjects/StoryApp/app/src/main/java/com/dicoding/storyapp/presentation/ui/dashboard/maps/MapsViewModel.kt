package com.dicoding.storyapp.presentation.ui.dashboard.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.storyapp.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(storyRepository: StoryRepository) : ViewModel() {

    val storiesLiveData = storyRepository.getAll(true).asLiveData()
}