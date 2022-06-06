package com.example.project3.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofitClient = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://food2fork.ca/api/recipe/")
        .build()
        .create(ApiInterface::class.java)!!
}