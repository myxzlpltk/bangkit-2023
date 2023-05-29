package com.bangkit.dessert.core.utils

import com.bangkit.dessert.core.data.source.local.entity.DessertBriefEntity
import com.bangkit.dessert.core.data.source.remote.response.DessertBriefItemResponse
import com.bangkit.dessert.core.domain.model.DessertBrief

object DessertBriefMapper {

    fun fromLocal(desserts: List<DessertBriefEntity>) = desserts.map { fromLocal(it) }
    fun fromLocal(dessert: DessertBriefEntity) = DessertBrief(
        id = dessert.id,
        name = dessert.name,
        image = dessert.image
    )

    fun fromRemote(response: List<DessertBriefItemResponse>) = response.map {
        DessertBrief(
            id = it.idMeal,
            name = it.strMeal,
            image = it.strMealThumb
        )
    }

    fun toLocal(desserts: List<DessertBrief>) = desserts.map { toLocal(it) }
    fun toLocal(dessert: DessertBrief) = DessertBriefEntity(
        id = dessert.id,
        name = dessert.name,
        image = dessert.image,
    )
}