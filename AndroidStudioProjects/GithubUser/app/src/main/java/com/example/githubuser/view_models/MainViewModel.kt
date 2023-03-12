package com.example.githubuser.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.networks.ApiConfig
import com.example.githubuser.networks.SearchUsersResponse
import com.example.githubuser.networks.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel : ViewModel() {

    companion object {
        const val DEFAULT_QUERY = "saddam"
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PER_PAGE = 30
    }

    private val _users = MutableLiveData<List<UserResponse>>()
    val users: LiveData<List<UserResponse>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var total = 0
    private var query = DEFAULT_QUERY
    private var page = DEFAULT_PAGE

    init {
        loadUsers()
    }

    fun findUsers(newQuery: String) {
        if (query == newQuery) return

        /* Reset all query */
        query = newQuery.ifEmpty { DEFAULT_QUERY }
        page = DEFAULT_PAGE
        loadUsers()
    }

    fun loadUsers(isLoadMore: Boolean = false) {
        /* Cancel if still loading */
        if (_isLoading.value == true) return
        /* Cancel if in load more mode and all data already loaded */
        if (isLoadMore && (_users.value?.size ?: 0) >= total) return
        /* Start loading */
        _isLoading.value = true

        /* Fetch network */
        ApiConfig.getApiService().findUsers(query, page, DEFAULT_PER_PAGE)
            .enqueue(object : Callback<SearchUsersResponse> {
                override fun onResponse(
                    call: Call<SearchUsersResponse>,
                    response: Response<SearchUsersResponse>,
                ) {
                    _isLoading.value = false

                    /* Save data */
                    val data = response.body()
                    if (response.isSuccessful && data != null) {
                        if (isLoadMore) _users.value = _users.value?.plus(data.items)
                        else _users.value = data.items
                        total = data.totalCount
                        page++
                    } else {
                        Timber.e("Empty response on getAllUsers")
                    }
                }

                override fun onFailure(call: Call<SearchUsersResponse>, t: Throwable) {
                    _isLoading.value = false
                    Timber.e("Fetch getAllUsers failed: $t")
                    t.printStackTrace()
                }
            })
    }
}