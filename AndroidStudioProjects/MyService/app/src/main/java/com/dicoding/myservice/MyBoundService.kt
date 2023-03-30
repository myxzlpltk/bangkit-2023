package com.dicoding.myservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class MyBoundService : Service() {

    companion object {
        internal val TAG = MyBoundService::class.java.simpleName
    }

    private val binder = MyBinder()
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    val numberLiveData = MutableLiveData<Int>()

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "Service started")
        serviceScope.launch {
            for (i in 1..50) {
                delay(1000)
                Log.d(TAG, "Do something $i")
                numberLiveData.postValue(i)
            }

            Log.d(TAG, "Service stopped")
        }

        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "Service destroyed")
        serviceJob.cancel()
        return super.onUnbind(intent)
    }

    internal inner class MyBinder : Binder() {
        val getService: MyBoundService = this@MyBoundService
    }
}