package com.example.newproject.repo

import com.example.newproject.RoomDb.FavoriteDrink
import com.example.newproject.RoomDb.FavoriteDrinkDao
import com.example.newproject.network.ApiInterface
import javax.inject.Inject

class Repo @Inject constructor(val apiService:ApiInterface,val drinksdao: FavoriteDrinkDao) {

    suspend fun searchByName(name: String) = apiService.searchByName(name)
    suspend fun searchByAlphabet(alphabet: Char) = apiService.searchByAlphabet(alphabet)
    suspend fun getFavorites() = drinksdao.getAllFavorites()
    suspend fun addToFavorites(drink: FavoriteDrink) = drinksdao.insert(drink)
    suspend fun removeFromFavorites(drink: FavoriteDrink) = drinksdao.delete(drink)

}