package com.example.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.data.local.room.UserDao
import com.example.githubuser.data.remote.retrofit.ApiService
import com.example.githubuser.helper.Event
import com.example.githubuser.helper.ResultState

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
) {

    /**
     * Get user from network or database
     * @param username Username of the user
     * @return UserEntity
     */
    fun getUser(username: String): LiveData<ResultState<UserEntity>> = liveData {
        // Send loading state
        emit(ResultState.Loading)

        // Check database
        val localData: LiveData<ResultState<UserEntity>> = userDao.getUserByUsername(username).map {
            // Send loading if not found
            if (it == null) ResultState.Loading
            // Send success with data if it found
            else ResultState.Success(it)
        }
        // Hook up the live data source
        emitSource(localData)

        // Try fetch the user from network
        try {
            // Get the response
            val response = apiService.getUser(username)
            // Check if the user is favorite from db
            val isFavorite = userDao.isFavorite(response.username)
            // Create UserEntity
            val user = UserEntity.fromUserResponse(response, isFavorite)
            // Insert into database
            userDao.insertUser(user)
        } catch (e: Exception) {
            // Send error
            emit(ResultState.Error(Event("Network error! Please try again later")))
        }
    }

    /**
     * Get favorite users from database only
     */
    fun getFavoriteUsers(): LiveData<ResultState<List<UserEntity>>> = liveData {
        // Send loading state
        emit(ResultState.Loading)

        // Check database
        val localData: LiveData<ResultState<List<UserEntity>>> = userDao.getFavoriteUsers().map {
            ResultState.Success(it)
        }
        // Hook up the live data source
        emitSource(localData)
    }

    suspend fun setFavoriteUser(user: UserEntity, isFavorite: Boolean) {
        user.isFavorite = isFavorite
        userDao.updateUser(user)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiService, userDao)
        }.also { instance = it }
    }
}