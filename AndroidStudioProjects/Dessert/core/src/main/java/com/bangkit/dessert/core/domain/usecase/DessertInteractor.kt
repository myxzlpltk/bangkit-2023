package com.bangkit.dessert.core.domain.usecase

import com.bangkit.dessert.core.data.Resource
import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import com.bangkit.dessert.core.domain.repository.DessertRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DessertInteractor @Inject constructor(
    private val dessertRepository: DessertRepository
) : DessertUseCase {

    override suspend fun refresh() {
        dessertRepository.refresh()
    }

    override fun getAll(): Flow<Resource<List<DessertBrief>>> {
        return dessertRepository.getAll()
    }

    override fun getOne(id: String): Flow<Resource<Dessert>> {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Flow<Resource<List<DessertBrief>>> {
        TODO("Not yet implemented")
    }

    override fun setFavorite(dessert: Dessert, state: Boolean) {
        TODO("Not yet implemented")
    }
}