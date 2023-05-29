package com.bangkit.dessert.core.data.source.local

import androidx.room.withTransaction
import com.bangkit.dessert.core.data.source.local.entity.DessertBriefEntity
import com.bangkit.dessert.core.data.source.local.room.DessertBriefDao
import com.bangkit.dessert.core.data.source.local.room.DessertDao
import com.bangkit.dessert.core.data.source.local.room.DessertDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DessertLocalDataSource @Inject constructor(
    private val database: DessertDatabase,
    private val dessertDao: DessertDao,
    private val dessertBriefDao: DessertBriefDao
) {

    fun getAll(): Flow<List<DessertBriefEntity>> = dessertBriefDao.getAll()

    suspend fun insertAll(desserts: List<DessertBriefEntity>) {
        database.withTransaction {
            dessertBriefDao.clearAll()
            dessertBriefDao.insert(desserts)
        }
    }
}