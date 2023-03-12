package com.example.githubuser.networks

import com.example.githubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private lateinit var apiService: ApiService

        fun getApiService(): ApiService {
            /* Return existing api service if exists */
            if (::apiService.isInitialized) return apiService

            /* Conditional logging interceptor */
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            /* Setup auth interceptor to add token */
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", "Bearer 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").build()
                chain.proceed(requestHeaders)
            }

            /* Build client */
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor).build()

            /* Create retrofit service */
            val retrofit = Retrofit.Builder().baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()

            /* Set new api service */
            apiService = retrofit.create(ApiService::class.java)

            /* Return api service */
            return apiService
        }
    }
}