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

    /* Get user from DB first then network */
    fun getUser(username: String): LiveData<ResultState<UserEntity>> = liveData {
        emit(ResultState.Loading)

        val localData: LiveData<ResultState<UserEntity>> = userDao.getUserByUsername(username).map {
            if (it == null) ResultState.Loading else ResultState.Success(it)
        }
        emitSource(localData)

        try {
            val response = apiService.getUser(username)
            val isFavorite = userDao.isFavorite(response.username)
            val user = UserEntity.fromUserResponse(response, isFavorite)

            userDao.insertUser(user)
        } catch (e: Exception) {
            emit(ResultState.Error(Event("Network error! Please try again later")))
        }
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