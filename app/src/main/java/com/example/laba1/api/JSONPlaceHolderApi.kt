package com.example.laba1.api

import com.example.laba1.db.entities.Recipe
import retrofit2.Call
import retrofit2.http.GET


interface JSONPlaceHolderApi {

    @GET("recipes2022.json")
    fun getRecipes(): Call<List<Recipe>>
}