package com.dicoding.githubusercompose.data.repositories

import com.dicoding.githubusercompose.data.entities.User
import com.dicoding.githubusercompose.data.local.database.GithubDatabase
import com.dicoding.githubusercompose.data.responses.toUser
import com.dicoding.githubusercompose.data.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
                val user = userService.getByLogin(login).toUser()
                user.isFavorite = database.userDao().isFavorite(user.login)
                database.userDao().insert(user)
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

    fun getFavorites(): Flow<List<User>> {
        return database.userDao().getFavorites()
    }

    suspend fun toggleFavorite(user: User) {
        toggleFavorite(user.login)
    }

    suspend fun toggleFavorite(login: String) {
        database.userDao().toggleFavorite(login)
    }
}