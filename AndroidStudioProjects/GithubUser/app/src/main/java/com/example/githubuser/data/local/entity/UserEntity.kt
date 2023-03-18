package com.example.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.githubuser.data.remote.response.UserResponse
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users", indices = [Index(value = ["username"], unique = true)])
class UserEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "username")
    private val _username: String,

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
) : Parcelable {
    val username get() = _username.lowercase()

    companion object {
        fun fromUserResponse(userResponse: UserResponse, isFavorite: Boolean): UserEntity {
            return UserEntity(
                userResponse.id,
                userResponse.username,
                userResponse.name,
                userResponse.avatarUrl,
                userResponse.bio,
                userResponse.publicRepos,
                userResponse.following,
                userResponse.followers,
                isFavorite,
            )
        }
    }
}