package com.dicoding.githubusercompose.ui.about_me

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubusercompose.R
import com.dicoding.githubusercompose.data.repositories.UserRepository
import com.dicoding.githubusercompose.di.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AboutMeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val resourcesProvider: ResourcesProvider,
) : ViewModel() {

    private val login = "myxzlpltk"

    init {
        viewModelScope.launch {
            userRepository.getByLogin(login).collect { result ->
                result.onSuccess { user ->
                    _stateFlow.update {
                        AboutMeState.Success(user)
                    }
                }

                result.onFailure { throwable ->
                    val message = when (throwable) {
                        is IOException -> resourcesProvider.getString(R.string.disk_error)
                        is HttpException -> resourcesProvider.getString(R.string.network_error)
                        else -> resourcesProvider.getString(R.string.generic_error)
                    }

                    _stateFlow.update {
                        AboutMeState.Error(message)
                    }
                }
            }
        }
    }

    private val _stateFlow = MutableStateFlow<AboutMeState>(AboutMeState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

}