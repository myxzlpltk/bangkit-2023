package com.dicoding.githubusercompose.di

import android.content.Context
import androidx.room.Room
import com.dicoding.githubusercompose.data.local.database.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubDatabase {
        return Room.databaseBuilder(context, GithubDatabase::class.java, "github.db").build()
    }

    @Provides
    fun provideUserDao(database: GithubDatabase) = database.userDao()
}