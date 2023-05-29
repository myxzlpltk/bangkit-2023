package com.bangkit.dessert.core.domain.repository

import com.bangkit.dessert.core.data.Resource
import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import kotlinx.coroutines.flow.Flow

interface DessertRepository {

    // List dessert
    fun getAll(): Flow<Resource<List<DessertBrief>>>
    suspend fun refresh()

    // Single dessert
    fun getOne(id: String): Flow<Resource<Dessert>>

    // Favorites
    fun getFavorites(): Flow<Resource<List<DessertBrief>>>
    suspend fun setFavorite(dessert: Dessert, state: Boolean)
}