package com.example.githubuser.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.BuildConfig
import com.example.githubuser.R
import com.example.githubuser.shared.adapter.ListUserAdapter
import com.example.githubuser.databinding.ActivityHomeBinding
import com.example.githubuser.data.remote.response.SimpleUser
import timber.log.Timber

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<MainViewModel>()
    private val adapter = ListUserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Bind view */
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Setup android header */
        supportActionBar?.title = "All Github Users"

        /* Setup recycler view */
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadUsers()
                }
            }
        })

        /* Subscribe to many stuff */
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.users.observe(this) { setAllUsersData(it) }
        viewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { text ->
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }
        }
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
                viewModel.findUsers(query)
                searchView.clearFocus()
                return true
            }
        })

        /* Reset query on close */
        menu.findItem(R.id.search)
            .setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem): Boolean = true
                override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                    viewModel.findUsers(MainViewModel.DEFAULT_QUERY)
                    return true
                }
            })

        return super.onCreateOptionsMenu(menu)
    }

    private fun setAllUsersData(users: List<SimpleUser>) {
        adapter.setListUser(users)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}