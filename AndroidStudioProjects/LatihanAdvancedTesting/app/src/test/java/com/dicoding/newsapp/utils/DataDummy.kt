package com.dicoding.newsapp.utils

import com.dicoding.newsapp.data.local.entity.NewsEntity

object DataDummy {
    val news = List(10) {
        NewsEntity(
            "Title $it",
            "2022-02-22T22:22:22Z",
            "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
            "https://www.dicoding.com/",
        )
    }
}