package com.dicoding.storyapp.data.source

import com.dicoding.storyapp.data.entity.User
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.data.remote.UserService
import com.dicoding.storyapp.utils.getErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSource @Inject constructor(private val userService: UserService) {

    suspend fun login(email: String, password: String): Flow<ApiResponse<User>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.login(email, password)
                emit(ApiResponse.Success(response.toUser()))
            } catch (e: Exception) {
                val message = getErrorMessage(e)
                emit(ApiResponse.Error(message))
            }
        }
    }

    suspend fun register(name: String, email: String, password: String): Flow<ApiResponse<User>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                userService.register(name, email, password)
                emitAll(login(email, password))
            } catch (e: Exception) {
                val message = getErrorMessage(e)
                emit(ApiResponse.Error(message))
            }
        }
    }
}