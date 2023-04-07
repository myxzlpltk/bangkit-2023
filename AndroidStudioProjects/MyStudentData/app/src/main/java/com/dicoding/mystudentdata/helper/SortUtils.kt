package com.dicoding.mystudentdata.helper

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    fun getSortedQuery(sortType: SortType): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder("SELECT * FROM student ").run {
            when (sortType) {
                SortType.ASCENDING -> {
                    append("ORDER BY name ASC")
                }
                SortType.DESCENDING -> {
                    append("ORDER BY name DESC")
                }
                SortType.RANDOM -> {
                    append("ORDER BY RANDOM()")
                }
            }
        }

        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}