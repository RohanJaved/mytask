package com.example.newproject.RoomDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_drinks")
data class FavoriteDrink(
    @PrimaryKey val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val isAlcoholic: Boolean
)