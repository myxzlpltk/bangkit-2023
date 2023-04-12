package com.dicoding.jetheroes.data

import com.dicoding.jetheroes.model.Hero
import com.dicoding.jetheroes.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero> {
        return HeroesData.heroes
    }
}