package com.example.githubuser.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.BuildConfig
import com.example.githubuser.R
import com.example.githubuser.adapters.ListUserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.networks.UserResponse
import com.example.githubuser.view_models.MainViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Bind view */
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Setup android header */
        supportActionBar?.title = "All Github Users"

        /* Setup recycler view */
        binding.rvUsers.setHasFixedSize(false)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) mainViewModel.loadUsers(true)
            }
        })


        /* Subscribe to many stuff */
        mainViewModel.isLoading.observe(this) { showLoading(it) }
        mainViewModel.users.observe(this) { setAllUsersData(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /* Bind menu */
        menuInflater.inflate(R.menu.search_menu, menu)

        /* Init search manager*/
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean = false
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUsers(query)
                searchView.clearFocus()
                return true
            }
        })

        /* Reset query on close */
        menu.findItem(R.id.search)
            .setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem): Boolean = true
                override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                    mainViewModel.findUsers(MainViewModel.DEFAULT_QUERY)
                    return true
                }
            })

        return super.onCreateOptionsMenu(menu)
    }

    private fun setAllUsersData(users: List<UserResponse>) {
        val recyclerViewState = binding.rvUsers.layoutManager?.onSaveInstanceState()
        binding.rvUsers.adapter = ListUserAdapter(users)
        binding.rvUsers.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}