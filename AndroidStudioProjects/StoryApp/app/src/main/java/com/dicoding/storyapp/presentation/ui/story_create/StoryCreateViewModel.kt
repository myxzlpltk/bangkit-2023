package com.dicoding.storyapp.presentation.ui.story_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.BaseViewModel
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.data.remote.response.CreateStoryResponse
import com.dicoding.storyapp.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class StoryCreateViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
) : BaseViewModel() {

    fun create(file: File, description: String): LiveData<ApiResponse<CreateStoryResponse>> {
        val result = MutableLiveData<ApiResponse<CreateStoryResponse>>()

        if (isBusy.value == false) {
            postBusy(true)
            viewModelScope.launch {
                storyRepository.create(file, description).collect {
                    if (it is ApiResponse.Success || it is ApiResponse.Error) postBusy(false)
                    result.postValue(it)
                }
            }
        }

        return result
    }
}