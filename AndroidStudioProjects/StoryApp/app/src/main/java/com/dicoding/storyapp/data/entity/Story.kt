package com.dicoding.storyapp.data.entity

import java.util.*

data class Story(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: Date,
    val lat: Double?,
    val lon: Double?,
)
