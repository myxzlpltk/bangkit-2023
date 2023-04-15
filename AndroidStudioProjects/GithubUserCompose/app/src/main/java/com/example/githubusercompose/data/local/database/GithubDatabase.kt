package com.example.githubusercompose.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubusercompose.data.entities.User
import com.example.githubusercompose.data.local.dao.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}