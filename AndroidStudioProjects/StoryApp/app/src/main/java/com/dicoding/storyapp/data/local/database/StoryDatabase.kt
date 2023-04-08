package com.dicoding.storyapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dicoding.storyapp.data.entity.RemoteKeys
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.local.Converters
import com.dicoding.storyapp.data.local.dao.RemoteKeysDao
import com.dicoding.storyapp.data.local.dao.StoryDao

@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}