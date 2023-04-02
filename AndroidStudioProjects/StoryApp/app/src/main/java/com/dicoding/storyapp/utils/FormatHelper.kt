package com.dicoding.storyapp.utils

import java.text.DateFormat
import java.util.*

fun Date.toLocaleFormat(): String {
    return DateFormat.getDateInstance(DateFormat.FULL).format(this)
}