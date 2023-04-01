package com.dicoding.storyapp.presentation.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.preference.UserPreference
import com.dicoding.storyapp.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val pref: UserPreference,
    private val storyRepository: StoryRepository,
) : ViewModel() {

    val pagingData = MutableLiveData<PagingData<Story>>()
    var job: Job? = null

    fun onEvent(event: StoryEvent) {
        when (event) {
            is StoryEvent.ScreenLoad, is StoryEvent.SwipeToRefreshEvent -> fetchData()
            else -> {}
        }
    }

    private fun fetchData() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            val token = pref.getToken()
            storyRepository.getStories(token).cachedIn(viewModelScope).collectLatest {
                pagingData.postValue(it)
            }
        }
    }
}