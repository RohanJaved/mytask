package com.example.newproject.datamodels

 class DrinkResponse(
    val drinks: ArrayList<Drink>?
)

data class Drink(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val strAlcoholic: String
)
