package com.example.githubuser.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.networks.ApiConfig
import com.example.githubuser.networks.UserResponse
import com.example.githubuser.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFollowViewModel : ViewModel() {

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PER_PAGE = 30
    }

    private val _users = MutableLiveData<List<UserResponse>>(emptyList())
    val users: LiveData<List<UserResponse>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private var page = DEFAULT_PAGE
    val isFirstPage get() = page == MainViewModel.DEFAULT_PAGE

    fun findFollow(type: String, username: String, total: Int) {
        if (total == 0) {
            _isLoading.value = false
            return
        }

        page = DEFAULT_PAGE
        _users.value = emptyList()

        loadFollow(type, username, total)
    }

    fun loadFollow(type: String, username: String, total: Int) {
        /* Cancel if still loading */
        if (_isLoading.value == true) return
        /* Cancel if all data already loaded */
        if ((_users.value?.size ?: 0) >= total) {
            _isLoading.value = false
            return
        }
        /* Start loading */
        _isLoading.value = true

        /* Fetch network */
        ApiConfig.getApiService().findFollow(username, type, page, DEFAULT_PER_PAGE)
            .enqueue(object : Callback<List<UserResponse>> {
                override fun onResponse(
                    call: Call<List<UserResponse>>,
                    response: Response<List<UserResponse>>,
                ) {
                    _isLoading.value = false

                    /* Save data */
                    val data = response.body()
                    if (response.isSuccessful && data != null) {
                        _users.value = _users.value?.plus(data)
                        page++
                    } else {
                        _toastText.value = Event("There is no data to be found")
                    }
                }

                override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                    _isLoading.value = false
                    _toastText.value = Event("Something went wrong. Check your network")
                }
            })
    }
}