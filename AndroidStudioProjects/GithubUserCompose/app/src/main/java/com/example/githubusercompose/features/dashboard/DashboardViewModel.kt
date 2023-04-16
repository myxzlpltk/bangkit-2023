package com.example.githubusercompose.features.dashboard

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.githubusercompose.config.PAGE_SIZE
import com.example.githubusercompose.data.entities.User
import com.example.githubusercompose.data.paging.SearchUserPagingSource
import com.example.githubusercompose.data.paging.UserPagingSource
import com.example.githubusercompose.data.repositories.UserRepository
import com.example.githubusercompose.data.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
    private val userService: UserService
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(DashboardState())
    val stateFlow = _stateFlow.asStateFlow()

    private var pagingSource: PagingSource<Int, User> = UserPagingSource(userService)

    init {
        viewModelScope.launch {
            stateFlow.map { it.query.text }.distinctUntilChanged().debounce(500)
                .collectLatest { query ->
                    Timber.d("QUERY: $query")
                    pagingSource.invalidate()
                    pagingSource = if (query.isNotBlank()) {
                        SearchUserPagingSource(query.trim(), userService)
                    } else {
                        UserPagingSource(userService)
                    }
                }
        }
    }

    val pager = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { pagingSource }
    ).flow

    fun openSearch() {
        _stateFlow.value = _stateFlow.value.copy(
            query = TextFieldValue(""),
            search = true,
        )
    }

    fun clearSearch() {
        _stateFlow.value = _stateFlow.value.copy(query = TextFieldValue(""))
    }

    fun closeSearch() {
        _stateFlow.value = _stateFlow.value.copy(
            query = TextFieldValue(""),
            search = false
        )
    }

    fun onValueChange(value: TextFieldValue) {
        if (_stateFlow.value.search) {
            _stateFlow.value = _stateFlow.value.copy(query = value)
        }
    }
}