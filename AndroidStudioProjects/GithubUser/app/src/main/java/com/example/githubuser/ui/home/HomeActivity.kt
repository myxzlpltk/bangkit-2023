package com.example.githubuser.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.iterator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.BuildConfig
import com.example.githubuser.R
import com.example.githubuser.data.remote.response.SimpleUser
import com.example.githubuser.databinding.ActivityHomeBinding
import com.example.githubuser.shared.adapter.ListUserAdapter
import com.example.githubuser.shared.util.AppUtils
import com.example.githubuser.ui.settings.SettingPreferences
import com.example.githubuser.ui.settings.SettingsActivity
import timber.log.Timber

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModel.Factory(SettingPreferences.getInstance(this))
    }
    private val adapter = ListUserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Bind view */
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        setContentView(binding.root)

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
        viewModel.getThemeSetting().observe(this) { which -> AppUtils.setTheme(which) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /* Bind menu */
        menuInflater.inflate(R.menu.search_menu, menu)

        /* Init search manager*/
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
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
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(searchItem: MenuItem): Boolean {
                setItemsVisibility(menu, searchItem, false)
                return true
            }

            override fun onMenuItemActionCollapse(searchItem: MenuItem): Boolean {
                setItemsVisibility(menu, searchItem, true)
                viewModel.findUsers(HomeViewModel.DEFAULT_QUERY)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@HomeActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setItemsVisibility(menu: Menu, exception: MenuItem, visible: Boolean) {
        menu.iterator().forEach { if (it != exception) it.isVisible = visible }
    }

    private fun setAllUsersData(users: List<SimpleUser>) {
        adapter.setListUser(users)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}