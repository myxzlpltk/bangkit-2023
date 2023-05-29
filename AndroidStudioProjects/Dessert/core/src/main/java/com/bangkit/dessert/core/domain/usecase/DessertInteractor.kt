package com.bangkit.dessert.core.domain.usecase

import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import com.bangkit.dessert.core.domain.repository.DessertRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DessertInteractor @Inject constructor(
    private val dessertRepository: DessertRepository
) : DessertUseCase {

    override fun getAll(): Flow<Result<List<DessertBrief>>> {
        TODO("Not yet implemented")
    }

    override fun getOne(id: String): Flow<Result<Dessert>> {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Flow<Result<List<DessertBrief>>> {
        TODO("Not yet implemented")
    }

    override fun setFavorite(dessert: Dessert, state: Boolean) {
        TODO("Not yet implemented")
    }
}