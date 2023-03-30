package com.dicoding.myservice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.myservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupService()
    }

    private fun setupService() {
        val serviceIntent = Intent(this, MyBackgroundService::class.java)

        binding.btnStartBackgroundService.setOnClickListener { startService(serviceIntent) }
        binding.btnStopBackgroundService.setOnClickListener { stopService(serviceIntent) }
    }
}