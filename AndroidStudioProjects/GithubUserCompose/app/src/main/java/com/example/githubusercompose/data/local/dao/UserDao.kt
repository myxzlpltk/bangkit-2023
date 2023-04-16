package com.example.githubusercompose.data.local.dao

import androidx.room.*
import com.example.githubusercompose.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY login")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE is_favorite = 1 ORDER BY login")
    fun getFavorites(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    fun getByLogin(login: String): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("DELETE FROM users WHERE is_favorite = 0")
    suspend fun delete()

    @Query("DELETE FROM users WHERE login = :login")
    suspend fun deleteByLogin(login: String)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE login = :login AND is_favorite = 1)")
    suspend fun isFavorite(login: String): Boolean
}