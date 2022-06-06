package com.example.project3.repository

import com.example.project3.model.CookingRecipe
import com.example.project3.retrofit.RetrofitClient
import retrofit2.Response
import java.lang.Exception

class Repository {
    suspend fun getRecipeData(auth:String, page:Int, query:String):Response<CookingRecipe>?{
        return try{
            var response = RetrofitClient.retrofitClient.getData(auth, page, query)
            if (response.isSuccessful){
                response
            }else{
                null
            }
        }catch (ex:Exception){
            null
        }
    }
}