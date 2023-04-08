package com.dicoding.storyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.storyapp.data.entity.RemoteKeys
import com.dicoding.storyapp.utils.Configuration.REMOTE_KEY_TABLE

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM $REMOTE_KEY_TABLE WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeys?

    @Query("DELETE FROM $REMOTE_KEY_TABLE")
    suspend fun deleteRemoteKeys()
}