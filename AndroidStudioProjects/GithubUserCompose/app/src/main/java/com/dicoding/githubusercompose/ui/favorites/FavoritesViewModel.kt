package com.dicoding.githubusercompose.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubusercompose.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            userRepository.getFavorites().collect { favoriteUsers ->
                _stateFlow.update { state ->
                    state.copy(loading = false, favoriteUsers = favoriteUsers)
                }
            }
        }
    }

    private val _stateFlow: MutableStateFlow<FavoritesState> = MutableStateFlow(FavoritesState())
    val stateFlow: StateFlow<FavoritesState> = _stateFlow.asStateFlow()

    fun unfavorite(login: String) {
        viewModelScope.launch {
            userRepository.toggleFavorite(login)
        }
    }

}