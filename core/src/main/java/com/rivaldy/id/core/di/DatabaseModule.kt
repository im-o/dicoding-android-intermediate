package com.rivaldy.id.core.di

import android.content.Context
import androidx.room.Room
import com.rivaldy.id.core.data.datasource.local.db.AppDatabase
import com.rivaldy.id.core.utils.UtilConstants.DB_USER_STORY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Created by github.com/im-o on 10/20/2022. */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_USER_STORY).build()
    }

    fun stackWidgetAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_USER_STORY).build()
    }
}