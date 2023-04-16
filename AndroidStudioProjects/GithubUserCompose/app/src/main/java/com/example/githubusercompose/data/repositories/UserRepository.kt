package com.example.githubusercompose.data.repositories

import com.example.githubusercompose.data.entities.User
import com.example.githubusercompose.data.local.database.GithubDatabase
import com.example.githubusercompose.data.responses.toUser
import com.example.githubusercompose.data.services.UserService
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userService: UserService,
    private val database: GithubDatabase,
) {

    fun getByLogin(login: String): Flow<Result<User>> {
        return flow {
            /* Local */
            database.userDao().getByLogin(login).first()?.let { emit(Result.success(it)) }

            /* Remote */
            try {
                val user = userService.getByLogin(login)
                database.userDao().insert(user.toUser())
            } catch (e: Exception) {
                emit(Result.failure(e))
            }

            /* Local */
            val flowLocal = database.userDao().getByLogin(login)
                .filterNotNull()
                .map { Result.success(it) }
            emitAll(flowLocal)
        }
    }

    suspend fun updateUserLocal(user: User) {
        database.userDao().update(user)
    }
}