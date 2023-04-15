package com.example.githubusercompose.features.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusercompose.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            userRepository.getAll().collect { users ->
                _stateFlow.value = _stateFlow.value.copy(users = users)
            }
        }
    }

    private val _stateFlow: MutableStateFlow<DashboardState> = MutableStateFlow(DashboardState())
    val stateFlow: StateFlow<DashboardState> = _stateFlow.asStateFlow()

}