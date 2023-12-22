package com.example.laba1.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object  networkservice {
    private var mInstance: networkservice? = null
    val BASE_URL = "https://raw.githubusercontent.com/Lpirskaya/JsonLab/master/" //адрес сервера

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}