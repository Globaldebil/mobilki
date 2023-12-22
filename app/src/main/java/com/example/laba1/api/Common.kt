package com.example.laba1.api

object Common {
    val retrofitService: JSONPlaceHolderApi
        get() = networkservice.getClient().create(JSONPlaceHolderApi::class.java)
}