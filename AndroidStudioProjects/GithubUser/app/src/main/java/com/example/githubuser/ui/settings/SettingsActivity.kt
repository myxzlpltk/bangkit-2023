package com.example.githubuser.ui.settings

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivitySettingsBinding
import com.example.githubuser.shared.util.AppUtils

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val DEFAULT_THEME = 0
        val options = arrayOf("Follow System", "Light Theme", "Dark Theme")
    }

    private val viewModel by viewModels<SettingsViewModel> {
        SettingsViewModel.Factory(SettingPreferences.getInstance(this))
    }
    private val binding: ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }
    private var selectedTheme = DEFAULT_THEME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /* Setup action bar */
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /* Listen to live data */
        viewModel.getThemeSetting().observe(this) { which ->
            selectedTheme = which
            binding.themeSummary.text = options[which]
            AppUtils.setTheme(which)
        }

        /* Binding listener */
        binding.themeSetting.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.theme_setting -> showThemeDialog()
        }
    }

    private fun showThemeDialog() {
        val builder = AlertDialog.Builder(this).apply {
            setTitle("Select Theme")
            setSingleChoiceItems(options, selectedTheme) { dialog, which ->
                viewModel.saveThemeSetting(which)
                dialog.dismiss()
            }
            setNegativeButton("Cancel", null)
        }

        builder.create().show()
    }
}