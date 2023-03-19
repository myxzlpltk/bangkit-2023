package com.example.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.remote.response.UserResponse
import com.example.githubuser.data.remote.retrofit.ApiConfig
import com.example.githubuser.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFollowViewModel(
    private val type: String,
    private val username: String,
    private val total: Int,
) : ViewModel() {

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
    private val totalLoaded get() = _users.value?.size ?: 0

    init {
        if (total == 0) {
            _isLoading.value = false
        } else {
            loadFollow()
        }
    }

    fun loadFollow() {
        // Cancel if still loading
        if (_isLoading.value == true) return
        // Cancel if all data already loaded
        if (totalLoaded >= total) {
            _isLoading.value = false
            return
        }
        // Start loading
        _isLoading.value = true

        // Fetch network
        val apiRequest = if (type == "followers") {
            ApiConfig.getApiService().findFollowers(username, page, DEFAULT_PER_PAGE)
        } else {
            ApiConfig.getApiService().findFollowing(username, page, DEFAULT_PER_PAGE)
        }

        apiRequest.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>,
            ) {
                _isLoading.value = false

                // Save data
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

    class Factory(private val type: String, private val username: String, private val total: Int) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST") return ListFollowViewModel(type, username, total) as T
        }
    }
}