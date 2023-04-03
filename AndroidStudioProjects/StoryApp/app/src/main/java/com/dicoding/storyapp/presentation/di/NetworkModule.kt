package com.dicoding.storyapp.presentation.di

import com.dicoding.storyapp.data.preference.UserPreference
import com.dicoding.storyapp.data.remote.StoryService
import com.dicoding.storyapp.data.remote.UserService
import com.dicoding.storyapp.utils.Configuration.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesRetrofit(userPreference: UserPreference): Retrofit {
        val builder = OkHttpClient.Builder().apply {
            addNetworkInterceptor { chain ->
                val token = runBlocking { userPreference.getToken().first() }
                val request = chain.request().newBuilder().apply {
                    addHeader("Authorization", "Bearer $token")
                }.build()
                chain.proceed(request)
            }
        }

        return Retrofit.Builder().baseUrl(BASE_URL).client(builder.build())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun provideStoryService(retrofit: Retrofit): StoryService {
        return retrofit.create(StoryService::class.java)
    }
}