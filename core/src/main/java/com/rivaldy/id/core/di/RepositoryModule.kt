package com.rivaldy.id.core.di

import android.content.SharedPreferences
import com.rivaldy.id.core.data.data_source.local.pref.PreferenceRepositoryImpl
import com.rivaldy.id.core.data.data_source.remote.rest_api.ApiService
import com.rivaldy.id.core.data.data_source.remote.rest_api.RestApiRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Created by github.com/im-o on 10/1/2022. */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesPreferencesRepository(sharedPref: SharedPreferences) = PreferenceRepositoryImpl(sharedPref)

    @Provides
    fun providesRestApiRepository(apiService: ApiService) = RestApiRepositoryImpl(apiService)
}