package com.example.githubusercompose.data.repositories

import com.example.githubusercompose.data.entities.User
import com.example.githubusercompose.data.local.database.GithubDatabase
import com.example.githubusercompose.data.responses.toUser
import com.example.githubusercompose.data.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userService: UserService,
    private val database: GithubDatabase,
) {

    fun getAll(): Flow<List<User>> {
        return flow {
            val users = userService.getAll(0).map { it.toUser() }
            emit(users)
        }
    }
}