package com.bangkit.dessert

import android.app.Application
import com.google.android.material.color.DynamicColors

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Apply dynamic colors
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}