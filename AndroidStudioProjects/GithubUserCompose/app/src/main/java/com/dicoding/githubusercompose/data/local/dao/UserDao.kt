package com.dicoding.githubusercompose.data.local.dao

import androidx.room.*
import com.dicoding.githubusercompose.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE is_favorite = 1 ORDER BY login")
    fun getFavorites(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    fun getByLogin(login: String): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("UPDATE users SET is_favorite = NOT is_favorite WHERE login = :login")
    suspend fun toggleFavorite(login: String)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE login = :login AND is_favorite = 1)")
    suspend fun isFavorite(login: String): Boolean
}