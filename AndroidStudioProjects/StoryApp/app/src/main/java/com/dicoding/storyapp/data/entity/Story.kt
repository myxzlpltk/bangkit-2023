package com.dicoding.storyapp.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.storyapp.utils.Configuration.STORY_TABLE
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = STORY_TABLE)
data class Story(
    @ColumnInfo("id")
    @PrimaryKey
    val id: String,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("photo_url")
    val photoUrl: String,

    @ColumnInfo("created_at")
    val createdAt: Date,

    @ColumnInfo("latitude")
    val latitude: Double?,

    @ColumnInfo("longitude")
    val longitude: Double?,
) : Parcelable
