package com.example.newproject.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newproject.R
import com.example.newproject.RoomDb.FavoriteDrink
import com.example.newproject.datamodels.Drink
import com.example.newproject.datamodels.DrinkResponse
import com.example.newproject.network.NetworkResult
import com.example.newproject.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinksViewModel@Inject constructor(private val repo: Repo, val app: Application):ViewModel()
{
    val context: Context
    init {
        context = app.applicationContext
    }

    val drinksLiveData = MutableLiveData<List<Drink>>()
    val favoritesLiveData = MutableLiveData<List<FavoriteDrink>>()


    fun searchDrinksByName(name: String) {
        viewModelScope.launch {
            val response = repo.searchByName(name)
            drinksLiveData.postValue(response.drinks ?: emptyList())
        }
    }

    fun searchDrinksByAlphabet(letter: Char) {
        viewModelScope.launch {
            val response = repo.searchByAlphabet(letter)
            drinksLiveData.postValue(response.drinks ?: emptyList())
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            Log.e("TAG", "loadFavorites: the data coming in the favourite is ${repo.getFavorites()}", )
            favoritesLiveData.postValue(repo.getFavorites())
        }
    }

    fun toggleFavorite(drink: Drink, isFav: Boolean) {
        viewModelScope.launch {
            val favDrink = FavoriteDrink(
                idDrink = drink.idDrink,
                strDrink = drink.strDrink,
                strDrinkThumb = drink.strDrinkThumb,
                isAlcoholic = drink.strAlcoholic == "Alcoholic"
            )
            if(isFav){
                repo.addToFavorites(favDrink)
            }else{
                repo.removeFromFavorites(favDrink)
            }
            loadFavorites()
        }
    }









}