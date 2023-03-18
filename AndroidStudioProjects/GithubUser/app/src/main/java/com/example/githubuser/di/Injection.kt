package com.example.githubuser.di

import android.content.Context
import com.example.githubuser.data.UserRepository
import com.example.githubuser.data.local.room.GithubDatabase
import com.example.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = GithubDatabase.getInstance(context)
        val apiService = ApiConfig.getApiService()
        val userDao = database.userDao()

        return UserRepository.getInstance(apiService, userDao)
    }
}