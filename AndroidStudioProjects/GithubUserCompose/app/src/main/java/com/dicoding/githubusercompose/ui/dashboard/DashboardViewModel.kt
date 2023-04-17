package com.dicoding.githubusercompose.ui.dashboard

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.dicoding.githubusercompose.config.PAGE_SIZE
import com.dicoding.githubusercompose.data.entities.User
import com.dicoding.githubusercompose.data.paging.SearchUserPagingSource
import com.dicoding.githubusercompose.data.paging.UserPagingSource
import com.dicoding.githubusercompose.data.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userService: UserService
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(DashboardState())
    val stateFlow = _stateFlow.asStateFlow()

    private var pagingSource: PagingSource<Int, User> = UserPagingSource(userService)

    init {
        viewModelScope.launch {
            stateFlow.map { it.query.text }.distinctUntilChanged().debounce(500)
                .collectLatest { query ->
                    pagingSource.invalidate()
                    pagingSource = if (query.isNotBlank()) {
                        SearchUserPagingSource(query.trim(), userService)
                    } else {
                        UserPagingSource(userService)
                    }
                }
        }
    }

    val listState = LazyListState()
    val pager = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE),
        initialKey = 0,
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)

    /* Search View */
    fun toggleSearch() {
        _stateFlow.update {
            it.copy(searchVisible = !it.searchVisible, query = TextFieldValue(""))
        }
    }

    fun clearSearch() {
        _stateFlow.update {
            it.copy(query = TextFieldValue(""))
        }
    }

    fun updateSearch(value: TextFieldValue) {
        _stateFlow.update {
            it.copy(query = if (it.searchVisible) value else TextFieldValue(""))
        }
    }

    /* Overflow Menu */
    fun toggleOverflowMenu() {
        _stateFlow.update {
            it.copy(overflowMenuVisible = !it.overflowMenuVisible)
        }
    }
}
