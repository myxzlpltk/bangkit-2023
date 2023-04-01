package com.dicoding.storyapp.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.storyapp.data.entity.User
import com.dicoding.storyapp.data.preference.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<User?> {
        return pref.getUser().asLiveData()
    }
}