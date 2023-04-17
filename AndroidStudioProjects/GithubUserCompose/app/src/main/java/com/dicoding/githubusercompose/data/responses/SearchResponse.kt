package com.dicoding.githubusercompose.data.responses

import com.google.gson.annotations.SerializedName

data class SearchResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("items")
    val items: List<UserResponse>,
)

fun SearchResponse.toListUser() = this.items.map { userResponse -> userResponse.toUser() }