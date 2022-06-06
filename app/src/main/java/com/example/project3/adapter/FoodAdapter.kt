package com.example.project3.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project3.R
import com.example.project3.model.Result
import com.example.project3.view.DetailFragment
import com.example.project3.view.SearchFragment
import com.example.project3.viewModel.RecipeViewModel

class FoodAdapter: RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    private var listItemFood:List<Result>? = null


    fun setData(list: List<Result>?){
        this.listItemFood = list
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var foodImage:ImageView = itemView.findViewById(R.id.food_image)
        var titleText:TextView = itemView.findViewById(R.id.title_text)
        var publisherText:TextView = itemView.findViewById(R.id.publisher_text)
        var adapterViewModel:RecipeViewModel = ViewModelProvider(itemView.context as ViewModelStoreOwner).get(RecipeViewModel::class.java)


        fun binding(item:Result){
            Glide.with(itemView.context).load(item.featured_image).into(foodImage)
            titleText.text = item.title
            publisherText.text = item.publisher
        }

        fun clickFoodImage(position: Int){
            foodImage.setOnClickListener{
                adapterViewModel.recipeActivity!!.replaceFragment()
                adapterViewModel.id = position
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.clickFoodImage(position)

        var itemFood = listItemFood!![position]
        if (itemFood == null){
            return
        }
        holder.binding(itemFood)
    }

    override fun getItemCount(): Int {
        return if (listItemFood != null){
            listItemFood!!.size
        }else{
            0
        }
    }

}