package com.dicoding.storyapp.presentation.di

import android.content.Context
import androidx.room.Room
import com.dicoding.storyapp.data.local.database.StoryDatabase
import com.dicoding.storyapp.utils.Configuration.DB_NAME
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
    fun provideDatabase(@ApplicationContext context: Context): StoryDatabase {
        return Room.databaseBuilder(context, StoryDatabase::class.java, DB_NAME).build()
    }

    @Singleton
    @Provides
    fun provideStoryDao(database: StoryDatabase) = database.storyDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(database: StoryDatabase) = database.remoteKeysDao()
}