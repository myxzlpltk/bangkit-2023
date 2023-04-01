package com.dicoding.storyapp.data.remote.response

import com.dicoding.storyapp.data.entity.Story
import com.google.gson.annotations.SerializedName
import java.util.*

data class StoriesResponse(

    @field:SerializedName("listStory")
    val listStory: List<ListStoryItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,
) {
    fun toListStory(): List<Story> = listStory.map {
        Story(it.id, it.name, it.description, it.photoUrl, it.createdAt, it.lat, it.lon)
    }
}

data class ListStoryItem(

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: Date,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double?,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: Double?,
)
