package com.bangkit.dessert.core.data

import com.bangkit.dessert.core.data.source.local.DessertLocalDataSource
import com.bangkit.dessert.core.data.source.remote.DessertRemoteDataSource
import com.bangkit.dessert.core.data.source.remote.network.ApiResponse
import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import com.bangkit.dessert.core.domain.repository.DessertRepository
import com.bangkit.dessert.core.utils.DessertBriefMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DessertRepositoryImpl @Inject constructor(
    private val local: DessertLocalDataSource, private val remote: DessertRemoteDataSource
) : DessertRepository {

    override fun getAll(): Flow<Resource<List<DessertBrief>>> =
        networkBoundResource(query = { local.getAll().map(DessertBriefMapper::fromLocal) },
            fetch = { remote.getAll() },
            saveFetchResult = { response ->
                if (response is ApiResponse.Success) {
                    val remoteList = response.data
                    val domainList = DessertBriefMapper.fromRemote(remoteList)
                    val localList = DessertBriefMapper.toLocal(domainList)
                    local.insertAll(localList)
                }
            },
            shouldFetch = { it.isEmpty() }).flowOn(Dispatchers.IO)

    override suspend fun refresh() {
        val response = remote.getAll()
        if (response is ApiResponse.Success) {
            val remoteList = response.data
            val domainList = DessertBriefMapper.fromRemote(remoteList)
            val localList = DessertBriefMapper.toLocal(domainList)
            local.insertAll(localList)
        }
    }

    override fun getOne(id: String): Flow<Resource<Dessert>> {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Flow<Resource<List<DessertBrief>>> {
        TODO("Not yet implemented")
    }

    override suspend fun setFavorite(dessert: Dessert, state: Boolean) {
        TODO("Not yet implemented")
    }
}