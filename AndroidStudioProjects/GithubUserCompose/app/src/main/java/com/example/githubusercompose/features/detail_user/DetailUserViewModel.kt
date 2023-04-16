package com.example.githubusercompose.features.detail_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusercompose.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<DetailUserState>(DetailUserState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun setUser(login: String) {
        viewModelScope.launch {
            _stateFlow.value = DetailUserState.Loading
            userRepository.getByLogin(login).collect { result ->
                result.onSuccess { user ->
                    _stateFlow.value = DetailUserState.Success(user)
                }

                result.onFailure {
                    _stateFlow.value = DetailUserState.Error
                }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            if (_stateFlow.value is DetailUserState.Success) {
                val user = (_stateFlow.value as DetailUserState.Success).user
                user.isFavorite = !user.isFavorite

                userRepository.updateUserLocal(user)
            }
        }
    }

}