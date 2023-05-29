package com.bangkit.dessert.core.data

import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import com.bangkit.dessert.core.domain.repository.DessertRepository
import kotlinx.coroutines.flow.Flow

class DessertRepositoryImpl : DessertRepository {
    override suspend fun refresh() {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<Result<List<DessertBrief>>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshOne() {
        TODO("Not yet implemented")
    }

    override fun getOne(id: String): Flow<Result<Dessert>> {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Flow<Result<List<DessertBrief>>> {
        TODO("Not yet implemented")
    }

    override suspend fun setFavorite(dessert: Dessert, state: Boolean) {
        TODO("Not yet implemented")
    }
}