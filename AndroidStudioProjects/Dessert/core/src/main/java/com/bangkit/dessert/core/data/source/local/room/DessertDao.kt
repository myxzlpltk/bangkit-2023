package com.bangkit.dessert.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bangkit.dessert.core.data.source.local.entity.DessertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DessertDao {

    @Query("SELECT * FROM desserts")
    fun getAll(): Flow<List<DessertEntity>>

    @Query("SELECT * FROM desserts WHERE id = :id")
    fun getOne(id: Int): Flow<DessertEntity>

    @Query("SELECT * FROM desserts WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<DessertEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(desserts: List<DessertEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(dessert: DessertEntity)

    @Update
    suspend fun updateOne(dessert: DessertEntity)
}