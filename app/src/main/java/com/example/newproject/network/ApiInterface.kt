package com.example.newproject.network

import com.example.newproject.datamodels.DrinkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("search.php")
    suspend fun searchByName(@Query("s") name: String): DrinkResponse

    @GET("search.php")
    suspend fun searchByAlphabet(@Query("f") alphabet: Char): DrinkResponse
}
