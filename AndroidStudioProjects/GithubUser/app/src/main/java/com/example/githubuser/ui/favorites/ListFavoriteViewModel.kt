package com.example.githubuser.ui.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.UserRepository
import com.example.githubuser.di.Injection

class ListFavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFavoriteUsers() = userRepository.getFavoriteUsers()

    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ListFavoriteViewModel(userRepository) as T
        }

        companion object {
            @Volatile
            private var instance: Factory? = null
            fun getInstance(context: Context): Factory = instance ?: synchronized(this) {
                instance ?: Factory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}