package com.example.githubuser.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuser.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Bind view */
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Setup android header */
        supportActionBar?.title = "Github User List"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /*val searchManager = getSystemService<SearchManager>()
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)*/

        return super.onCreateOptionsMenu(menu)
    }
}