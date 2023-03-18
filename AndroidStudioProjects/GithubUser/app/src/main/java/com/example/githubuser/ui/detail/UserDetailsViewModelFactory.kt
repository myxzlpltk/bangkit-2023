package com.example.githubuser.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserDetailsViewModelFactory(private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return UserDetailsViewModel(username) as T
    }
}