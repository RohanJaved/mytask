package com.example.newproject.RoomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteDrink::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDrinkDao(): FavoriteDrinkDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "drink_database"
                ).build().also { instance = it }
            }
    }
}