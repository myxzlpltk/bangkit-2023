package com.dicoding.storyapp.presentation.ui.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.BaseViewModel
import com.dicoding.storyapp.data.entity.User
import com.dicoding.storyapp.data.preference.UserPreference
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val pref: UserPreference,
) : BaseViewModel() {

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            postBusy(true)
            userRepository.login(email, password).collect {
                if (it is ApiResponse.Success) pref.login(it.data)
                else if (it is ApiResponse.Error) {
                    postBusy(false)
                    postMessage(it.errorMessage)
                }
            }
        }
    }

    fun getUser(): LiveData<User?> {
        return pref.getUser().asLiveData()
    }
}