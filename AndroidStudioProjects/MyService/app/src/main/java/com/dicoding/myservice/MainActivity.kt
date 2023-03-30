package com.dicoding.myservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.myservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var boundService: MyBoundService
    private var boundStatus = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBoundService.MyBinder
            boundService = myBinder.getService
            boundStatus = true
            getNumberFromService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            boundStatus = false
        }
    }

    private fun getNumberFromService() {
        boundService.numberLiveData.observe(this) {
            binding.tvBoundServiceNumber.text = "Number $it"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupService()
    }

    private fun setupService() {
        val serviceIntent = Intent(this, MyBackgroundService::class.java)
        val foregroundServiceIntent = Intent(this, MyForegroundService::class.java)

        // Background service
        binding.btnStartBackgroundService.setOnClickListener { startService(serviceIntent) }
        binding.btnStopBackgroundService.setOnClickListener { stopService(serviceIntent) }

        // Foreground service
        binding.btnStartForegroundService.setOnClickListener {
            ContextCompat.startForegroundService(this, foregroundServiceIntent)
        }
        binding.btnStopForegroundService.setOnClickListener { stopService(foregroundServiceIntent) }

        // Bound service
        val boundServiceIntent = Intent(this, MyBoundService::class.java)
        binding.btnStartBoundService.setOnClickListener {
            bindService(boundServiceIntent, connection, BIND_AUTO_CREATE)
        }
        binding.btnStopBoundService.setOnClickListener {
            if(boundStatus) unbindService(connection)
        }
    }

    override fun onStop() {
        super.onStop()
        if (boundStatus) {
            unbindService(connection)
            boundStatus = false
        }
    }
}