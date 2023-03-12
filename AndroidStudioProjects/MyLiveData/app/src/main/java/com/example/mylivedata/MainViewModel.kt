package com.example.mylivedata

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {

    companion object {
        private const val ONE_SECOND = 1000
    }

    private val timer = Timer()
    private val mInitialTime = SystemClock.elapsedRealtime()
    private val mElapsedTime = MutableLiveData<Long>(0)

    init {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                mElapsedTime.postValue((SystemClock.elapsedRealtime() - mInitialTime) / ONE_SECOND)
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    fun getElapsedTime(): LiveData<Long> = mElapsedTime
}