package com.example.githubuser.ui.home

import androidx.lifecycle.*
import com.example.githubuser.data.remote.response.SearchResponse
import com.example.githubuser.data.remote.response.SimpleUser
import com.example.githubuser.data.remote.retrofit.ApiConfig
import com.example.githubuser.shared.util.Event
import com.example.githubuser.ui.settings.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref: SettingPreferences) : ViewModel() {

    companion object {
        const val DEFAULT_QUERY = "saddam"
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PER_PAGE = 30
        const val DEFAULT_TOTAL = Int.MAX_VALUE
    }

    fun getThemeSetting(): LiveData<Int> {
        return pref.getThemeSetting().asLiveData()
    }

    private val _users = MutableLiveData<List<SimpleUser>>(emptyList())
    val users: LiveData<List<SimpleUser>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private var total = DEFAULT_TOTAL
    private var query = DEFAULT_QUERY
    private var page = DEFAULT_PAGE

    init {
        loadUsers()
    }

    private val totalLoaded get() = _users.value?.size ?: 0

    fun findUsers(newQuery: String) {
        if (query == newQuery) return

        /* Reset all query */
        query = newQuery.ifEmpty { DEFAULT_QUERY }
        page = DEFAULT_PAGE
        total = DEFAULT_TOTAL
        _users.value = emptyList()

        loadUsers()
    }

    fun loadUsers() {
        /* Cancel if still loading */
        if (_isLoading.value == true) return
        /* Cancel if in load more mode and all data already loaded */
        if (totalLoaded >= total) return
        /* Start loading */
        _isLoading.value = true

        /* Fetch network */
        ApiConfig.getApiService().findUsers(query, page, DEFAULT_PER_PAGE)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>,
                ) {
                    _isLoading.value = false

                    /* Save data */
                    val data = response.body()
                    if (response.isSuccessful && data != null && data.items.isNotEmpty()) {
                        _users.value = _users.value?.plus(data.items)
                        total = data.totalCount
                        page++
                    } else {
                        _toastText.value = Event("There is no data to be found")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    _isLoading.value = false
                    _toastText.value = Event("Something went wrong. Check your network")
                }
            })
    }

    class Factory(private val pref: SettingPreferences) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(pref) as T
        }
    }
}