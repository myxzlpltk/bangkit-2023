package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY login")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE is_favorite = 1 ORDER BY login")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE login = :login")
    fun getUserByLogin(login: String): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(news: List<UserEntity>)

    @Update
    suspend fun updateUser(news: UserEntity)

    @Query("DELETE FROM users WHERE is_favorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE login = :login AND is_favorite = 1)")
    suspend fun isUserFavorite(login: String): Boolean
}