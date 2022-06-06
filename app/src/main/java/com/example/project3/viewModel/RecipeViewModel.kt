package com.example.project3.viewModel

import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3.model.CookingRecipe
import com.example.project3.model.Result
import com.example.project3.repository.Repository
import com.example.project3.view.RecipeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class RecipeViewModel:ViewModel() {
    var myResponse: MutableLiveData<Response<CookingRecipe>> = MutableLiveData()
    var recipeActivity:RecipeActivity? = null
    var resultData:MutableList<Result>? = mutableListOf()
    var id: Int? = null
    var currentPage: Int = 1
    var searchingKeyWord:String  = " "


    private val repository:Repository = Repository()

    fun getRecipeData(auth:String, page:Int, query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<CookingRecipe>? = repository.getRecipeData(auth, page, query)

            withContext(Dispatchers.Main) {
                myResponse.value = response
            }
        }
    }
}