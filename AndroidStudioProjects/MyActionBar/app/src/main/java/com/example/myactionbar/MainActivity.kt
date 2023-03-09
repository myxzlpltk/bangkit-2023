package com.example.myactionbar

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myactionbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                if (supportFragmentManager.fragments.filterIsInstance<MenuFragment>().isEmpty()) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MenuFragment()).addToBackStack(null)
                        .commit()
                    true
                } else {
                    false
                }
            }
            R.id.menu2 -> {
                startActivity(Intent(this, MenuActivity::class.java))
                true
            }
            else -> {
                true
            }
        }
    }
}