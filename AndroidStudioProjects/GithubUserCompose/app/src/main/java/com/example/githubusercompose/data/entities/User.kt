package com.example.githubusercompose.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users", indices = [Index(value = ["login"], unique = true)])
data class User(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "bio")
    val bio: String? = null,

    @ColumnInfo(name = "public_repo")
    val publicRepos: Int = 0,

    @ColumnInfo(name = "following")
    val following: Int = 0,

    @ColumnInfo(name = "followers")
    val followers: Int = 0,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,
) : Parcelable