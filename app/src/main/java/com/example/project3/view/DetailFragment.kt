package com.example.project3.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.project3.R
import com.example.project3.viewModel.RecipeViewModel


class DetailFragment : Fragment() {
    lateinit var detailViewModel:RecipeViewModel
    lateinit var detailImage:ImageView
    private lateinit var detailText:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        val view:View =  inflater.inflate(R.layout.fragment_detail, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.toolbar))
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)



        detailViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
        detailImage = view.findViewById(R.id.detail_image)
        detailText = view.findViewById(R.id.detail_text)

        Glide.with(requireContext()).load(detailViewModel.resultData!![detailViewModel.id!!].featured_image).into(detailImage)

        for (str:String in detailViewModel.resultData!![detailViewModel.id!!].ingredients){
            detailText.text = detailText.text.toString() + "-  " + str.trim() + "\n"
        }

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home){
            requireActivity().onBackPressed()
            true
        } else {
            Log.i("hello", item.itemId.toString())
            super.onOptionsItemSelected(item)
        }
    }
}