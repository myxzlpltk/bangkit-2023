package com.dicoding.storyapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Setup timber
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}