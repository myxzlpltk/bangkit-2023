package com.dicoding.storyapp.presentation.ui.sign_up

import androidx.lifecycle.viewModelScope
import com.dicoding.storyapp.BaseViewModel
import com.dicoding.storyapp.data.preference.UserPreference
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val pref: UserPreference,
) : BaseViewModel() {

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            postBusy(true)
            userRepository.register(name, email, password).collect {
                if (it is ApiResponse.Success) pref.login(it.data)
                else if (it is ApiResponse.Error) {
                    postBusy(false)
                    postMessage(it.errorMessage)
                }
            }
        }
    }
}