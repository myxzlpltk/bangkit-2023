package com.dicoding.myrecyclerview

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.myrecyclerview.model.Hero

class HeroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero)
        setupData()
    }

    private fun setupData() {
        val hero = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Hero", Hero::class.java) as Hero
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Hero>("Hero") as Hero
        }

        Glide.with(applicationContext)
            .load(hero.photo)
            .circleCrop()
            .into(findViewById(R.id.profileImageView))
        findViewById<TextView>(R.id.nameTextView).text = hero.name
        findViewById<TextView>(R.id.descTextView).text = hero.description
    }
}