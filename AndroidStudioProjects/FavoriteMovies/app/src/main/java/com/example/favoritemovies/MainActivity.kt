package com.example.favoritemovies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favoritemovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Favorite Movies"

        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.layoutManager = GridLayoutManager(this, 2)
        binding.rvMovies.adapter = ListMovieAdapter(listMovie)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_page)
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

}