package com.example.project3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.project3.R
import com.example.project3.viewModel.RecipeViewModel

class RecipeActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    lateinit var mViewModel:RecipeViewModel
    private val manager: FragmentManager = supportFragmentManager
    private lateinit var swipeRefreshLayout:SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        mViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        mViewModel.recipeActivity = this
        mViewModel.getRecipeData("Token 9c8b06d329136da358c2d00e76946b0111ce2c48", mViewModel.currentPage, mViewModel.searchingKeyWord)
        swipeRefreshLayout.setOnRefreshListener (this)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.teal_700))

        addFragment()
    }

    private fun addFragment(){
        val transaction:FragmentTransaction  = manager.beginTransaction()
        val fragment = SearchFragment()
        transaction.add(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    fun replaceFragment(){
        val transaction:FragmentTransaction  = manager.beginTransaction()
        val fragment = DetailFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onRefresh() {
        mViewModel.currentPage = 1
        mViewModel.getRecipeData("Token 9c8b06d329136da358c2d00e76946b0111ce2c48", mViewModel.currentPage, mViewModel.searchingKeyWord)
        var handler:Handler = Handler()
        handler.postDelayed(Runnable {
            swipeRefreshLayout.isRefreshing = false
        },300)
    }

}