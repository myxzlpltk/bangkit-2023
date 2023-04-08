package com.dicoding.storyapp

import com.dicoding.storyapp.data.entity.Story
import java.util.*

object DataDummy {
    val stories = List(100) { i ->
        Story(
            "story-$i",
            "Author $i",
            "Description $i",
            "https://example.com/$i.jpg",
            Date(),
            null,
            null,
        )
    }
}