package com.example.challengechapterlima.di

import android.content.Context
import androidx.room.Room
import com.example.challengechapterlima.local.AppDatabase
import com.example.challengechapterlima.local.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideFavoriteDao(appDatabase: AppDatabase): FavoriteDao =
        appDatabase.favoriteDao()
}