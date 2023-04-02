package com.dicoding.storyapp.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Story(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: Date,
    val lat: Double?,
    val lon: Double?,
) : Parcelable
