package com.example.newproject.RoomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDrinkDao {
    @Query("SELECT * FROM favorite_drinks")
    suspend fun getAllFavorites(): List<FavoriteDrink>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(drink: FavoriteDrink)

    @Delete
    suspend fun delete(drink: FavoriteDrink)
}