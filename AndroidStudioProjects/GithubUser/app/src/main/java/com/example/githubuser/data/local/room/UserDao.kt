package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY username")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE is_favorite = 1 ORDER BY username")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    fun getUserByUsername(username: String): LiveData<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(news: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(news: UserEntity)

    @Update
    suspend fun updateUser(news: UserEntity)

    @Query("DELETE FROM users WHERE is_favorite = 0")
    suspend fun deleteAll()

    @Query("DELETE FROM users WHERE username = :username")
    suspend fun deleteUserByUsername(username: String)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username AND is_favorite = 1)")
    suspend fun isFavorite(username: String): Boolean
}