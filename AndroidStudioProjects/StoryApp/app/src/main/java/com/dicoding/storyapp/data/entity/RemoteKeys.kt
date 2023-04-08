package com.dicoding.storyapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.storyapp.utils.Configuration.REMOTE_KEY_TABLE

@Entity(tableName = REMOTE_KEY_TABLE)
data class RemoteKeys(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,

    @ColumnInfo("prev_key")
    val prevKey: Int?,

    @ColumnInfo("next_key")
    val nextKey: Int?,

    @ColumnInfo("created_at")
    val createdAt: Long = System.currentTimeMillis(),
)
