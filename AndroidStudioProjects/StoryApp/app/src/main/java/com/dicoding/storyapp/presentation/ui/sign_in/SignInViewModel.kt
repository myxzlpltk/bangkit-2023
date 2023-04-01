package com.dicoding.storyapp.presentation.ui.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.data.entity.User
import com.dicoding.storyapp.data.preference.UserPreference
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val pref: UserPreference,
) : ViewModel() {

    fun login(email: String, password: String): LiveData<ApiResponse<User>> {
        val result = MutableLiveData<ApiResponse<User>>()
        viewModelScope.launch {
            userRepository.login(email, password).collect {
                if (it is ApiResponse.Success) pref.login(it.data)
                result.postValue(it)
            }
        }

        return result
    }
}