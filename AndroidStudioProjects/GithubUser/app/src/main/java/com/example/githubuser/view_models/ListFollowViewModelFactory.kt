package com.example.githubuser.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListFollowViewModelFactory(
    private val type: String,
    private val username: String,
    private val total: Int,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ListFollowViewModel(type, username, total) as T
    }
}