package com.example.githubusercompose.features.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.githubusercompose.config.PAGE_SIZE
import com.example.githubusercompose.data.paging.UserPagingSource
import com.example.githubusercompose.data.repositories.UserRepository
import com.example.githubusercompose.data.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
    userService: UserService
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(DashboardState())
    val stateFlow = _stateFlow.asStateFlow()

    val userPager = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        UserPagingSource(userService)
    }.flow
}