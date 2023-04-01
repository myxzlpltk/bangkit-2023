package com.dicoding.storyapp.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.storyapp.models.UserModel
import com.dicoding.storyapp.models.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<UserModel?> {
        return pref.getUser().asLiveData()
    }
}