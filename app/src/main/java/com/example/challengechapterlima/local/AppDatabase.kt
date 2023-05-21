package com.example.challengechapterlima.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.challengechapterlima.local.dao.FavoriteDao
import com.example.challengechapterlima.local.entity.FavoriteEntity


@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}