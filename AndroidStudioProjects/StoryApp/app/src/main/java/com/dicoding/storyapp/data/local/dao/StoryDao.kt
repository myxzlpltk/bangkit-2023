package com.dicoding.storyapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.utils.Configuration.STORY_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Query("SELECT * FROM $STORY_TABLE")
    fun pagingSource(): PagingSource<Int, Story>

    @Query("SELECT * FROM $STORY_TABLE ORDER BY RANDOM() LIMIT 1")
    fun getRandom(): Flow<Story>

    @Query("SELECT * FROM $STORY_TABLE WHERE id = :id")
    suspend fun getById(id: String): Story?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(story: List<Story>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(story: Story)

    @Query("DELETE FROM $STORY_TABLE")
    suspend fun deleteAll()
}