package com.dicoding.githubusercompose.ui.detail_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.githubusercompose.R
import com.dicoding.githubusercompose.data.repositories.UserRepository
import com.dicoding.githubusercompose.di.ResourcesProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class DetailUserViewModel @AssistedInject constructor(
    private val userRepository: UserRepository,
    private val resourcesProvider: ResourcesProvider,
    @Assisted private val login: String,
) : ViewModel() {

    init {
        viewModelScope.launch {
            userRepository.getByLogin(login).collect { result ->
                result.onSuccess { user ->
                    _stateFlow.update {
                        DetailUserState.Success(user)
                    }
                }

                result.onFailure { throwable ->
                    val message = when (throwable) {
                        is IOException -> resourcesProvider.getString(R.string.disk_error)
                        is HttpException -> resourcesProvider.getString(R.string.network_error)
                        else -> resourcesProvider.getString(R.string.generic_error)
                    }

                    _stateFlow.update {
                        DetailUserState.Error(message)
                    }
                }
            }
        }
    }

    private val _stateFlow = MutableStateFlow<DetailUserState>(DetailUserState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun toggleFavorite() {
        viewModelScope.launch {
            val state = _stateFlow.value
            if (state is DetailUserState.Success) {
                val user = state.user.apply { isFavorite = !isFavorite }
                userRepository.update(user)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(login: String): DetailUserViewModel
    }

    companion object {
        fun provideDetailUserViewModel(factory: Factory, login: String): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return factory.create(login) as T
                }
            }
        }
    }
}
