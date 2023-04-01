package com.dicoding.storyapp.utils

import com.dicoding.storyapp.data.remote.response.ErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.HttpException

fun parseErrorResponse(errorBody: ResponseBody?): ErrorResponse? {
    return try {
        val gson = Gson()
        val type = object : TypeToken<ErrorResponse>() {}.type
        gson.fromJson<ErrorResponse>(errorBody!!.charStream(), type)
    } catch (e: java.lang.Exception) {
        null
    }
}

fun getErrorMessage(e: Exception): String {
    return if (e is HttpException) {
        val response = parseErrorResponse(e.response()?.errorBody())
        response?.message ?: e.message.toString()
    } else {
        e.toString()
    }
}