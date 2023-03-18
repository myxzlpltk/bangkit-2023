package com.example.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuser.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: GithubDatabase? = null

        fun getInstance(context: Context): GithubDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext, GithubDatabase::class.java, "Github.db"
                ).build().also { instance = it }
            }
        }
    }
}