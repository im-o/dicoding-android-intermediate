package com.rivaldy.id.core.di

import android.content.SharedPreferences
import com.rivaldy.id.core.data.datasource.local.db.AppDatabase
import com.rivaldy.id.core.data.datasource.local.db.DbRepositoryImpl
import com.rivaldy.id.core.data.datasource.local.pref.PreferenceRepositoryImpl
import com.rivaldy.id.core.data.datasource.remote.rest.ApiService
import com.rivaldy.id.core.data.datasource.remote.rest.RestApiRepositoryImpl
import com.rivaldy.id.core.data.paging.repository.StoryPagingRepository
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

    @Provides
    fun providesDbRepository(appDatabase: AppDatabase) = DbRepositoryImpl(appDatabase)

    @Provides
    fun providesStoryPagingRepository(apiService: ApiService) = StoryPagingRepository(apiService)
}