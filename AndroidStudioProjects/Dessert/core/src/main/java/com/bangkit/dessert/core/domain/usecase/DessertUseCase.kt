package com.bangkit.dessert.core.domain.usecase

import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import kotlinx.coroutines.flow.Flow

interface DessertUseCase {
    fun getAll(): Flow<Result<List<DessertBrief>>>
    fun getOne(id: String): Flow<Result<Dessert>>
    fun getFavorites(): Flow<Result<List<DessertBrief>>>
    fun setFavorite(dessert: Dessert, state: Boolean)
}