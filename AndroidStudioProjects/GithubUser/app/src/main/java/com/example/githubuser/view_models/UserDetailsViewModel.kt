package com.example.githubuser.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.networks.ApiConfig
import com.example.githubuser.networks.UserDetailsResponse
import com.example.githubuser.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsViewModel : ViewModel() {

    private val _user = MutableLiveData<UserDetailsResponse>()
    val user: LiveData<UserDetailsResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    fun getUser(username: String) {
        /* Cancel if still loading */
        if (_isLoading.value == true) return
        if (_user.value is UserDetailsResponse) return
        /* Start loading */
        _isLoading.value = true

        /* Fetch network */
        ApiConfig.getApiService().findUser(username)
            .enqueue(object : Callback<UserDetailsResponse> {
                override fun onResponse(
                    call: Call<UserDetailsResponse>,
                    response: Response<UserDetailsResponse>,
                ) {
                    _isLoading.value = false

                    /* Save data */
                    if (response.isSuccessful) {
                        _user.value = response.body() as UserDetailsResponse
                    } else {
                        _toastText.value = Event("There is no data to be found")
                    }
                }

                override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                    _isLoading.value = false
                    _toastText.value = Event("Something went wrong. Check your network")
                }
            })
    }
}