package com.example.githubusercompose.di

import android.content.Context
import androidx.room.Room
import com.example.githubusercompose.data.local.database.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubDatabase {
        return Room.databaseBuilder(context, GithubDatabase::class.java, "github.db").build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: GithubDatabase) = database.userDao()
}