package com.dicoding.storyapp.utils

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Parcelable
import java.io.File

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= TIRAMISU -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key)
}

fun Intent.file(key: String): File? = when {
    SDK_INT >= TIRAMISU -> getParcelableExtra(key, File::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key)
}