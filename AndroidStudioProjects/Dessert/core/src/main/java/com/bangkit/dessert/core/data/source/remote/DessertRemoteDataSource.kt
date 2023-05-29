package com.bangkit.dessert.core.data.source.remote

import com.bangkit.dessert.core.data.source.remote.network.ApiResponse
import com.bangkit.dessert.core.data.source.remote.network.DessertApiService
import com.bangkit.dessert.core.data.source.remote.response.ListDessertResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DessertRemoteDataSource @Inject constructor(private val apiService: DessertApiService) {

    suspend fun getAll(): ApiResponse<ListDessertResponse> {
        return try {
            val response = apiService.getAll()
            if (response.meals.isEmpty()) {
                ApiResponse.Empty
            } else {
                ApiResponse.Success(response)
            }
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }
}