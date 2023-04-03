package com.dicoding.storyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.storyapp.data.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun remoteKeyById(id: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE id = :id")
    suspend fun deleteById(id: String)
}