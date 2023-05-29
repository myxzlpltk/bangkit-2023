package com.bangkit.dessert.core.domain.repository

import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import kotlinx.coroutines.flow.Flow

interface DessertRepository {

    // List dessert
    suspend fun refresh()
    fun getAll(): Flow<Result<List<DessertBrief>>>

    // Single dessert
    suspend fun refreshOne()
    fun getOne(id: String): Flow<Result<Dessert>>

    // Favorites
    fun getFavorites(): Flow<Result<List<DessertBrief>>>
    suspend fun setFavorite(dessert: Dessert, state: Boolean)
}