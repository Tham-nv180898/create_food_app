package com.example.project3.retrofit

import com.example.project3.model.CookingRecipe
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/")
    suspend fun getData(@Header("Authorization") auth: String, @Query("page") page:Int, @Query("query")query: String): Response<CookingRecipe>
}