package com.dicoding.storyapp.data.repository

import com.dicoding.storyapp.data.entity.User
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.data.source.UserDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userDataSource: UserDataSource) {

    suspend fun login(email: String, password: String): Flow<ApiResponse<User>> {
        return userDataSource.login(email, password)
    }

    suspend fun register(name: String, email: String, password: String): Flow<ApiResponse<User>> {
        return userDataSource.register(name, email, password)
    }
}