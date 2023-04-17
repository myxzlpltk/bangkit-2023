package com.dicoding.githubusercompose.di

import com.dicoding.githubusercompose.ui.detail_user.DetailUserViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun detailUserViewModelFactory(): DetailUserViewModel.Factory

}