package com.example.project3.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.R
import com.example.project3.adapter.FoodAdapter
import com.example.project3.model.Result
import com.example.project3.until.PaginationScrollListener
import com.example.project3.viewModel.RecipeViewModel
import com.facebook.shimmer.ShimmerFrameLayout


class SearchFragment : Fragment() {
    private lateinit var viewModel:RecipeViewModel
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var progressBar:ProgressBar
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private lateinit var recipeData: List<Result>
    var isLoading = false
    var isLastPage = false
    private var totalPage = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_search, container, false)

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.my_toolbar))


        viewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
        mRecyclerView = view.findViewById(R.id.mRecycleView)
        shimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout)
        progressBar = view.findViewById(R.id.progressBar)

        shimmerFrameLayout.startShimmerAnimation()
        foodAdapter = FoodAdapter()

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        val layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.addItemDecoration(itemDecoration)
        mRecyclerView.adapter = foodAdapter

        mRecyclerView.addOnScrollListener(object:PaginationScrollListener(layoutManager){
            override fun loadMoreItem() {
                isLoading = true
                progressBar.visibility = View.VISIBLE
                viewModel.currentPage += 1
                loadNextPage()
            }
            override fun isLastPage(): Boolean {
                return isLastPage
            }
            override fun isLoading(): Boolean {
                return isLoading
            }
        })

        viewModel.myResponse.observe(requireActivity(), Observer {
            if(it != null) {

                var handler:Handler = Handler()
                handler.postDelayed(Runnable {
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
                    mRecyclerView.visibility = View.VISIBLE
                }, 500)

                recipeData= it.body()!!.results
                if (isLoading){
                    viewModel.resultData?.addAll(recipeData)
                    foodAdapter.setData(viewModel.resultData)
                    foodAdapter.notifyDataSetChanged()
                    progressBar.visibility = View.GONE
                    isLoading = false
                }else{
                    viewModel.resultData = recipeData as MutableList<Result>
                    foodAdapter.setData(viewModel.resultData)
                    foodAdapter.notifyDataSetChanged()
                    viewModel.currentPage = 1
                    isLastPage = false
                }
            }else{
                shimmerFrameLayout.startShimmerAnimation()
                shimmerFrameLayout.visibility = View.VISIBLE
                mRecyclerView.visibility = View.GONE
                Toast.makeText(this.context, "Having trouble connecting to the network", Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val searchManager: SearchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchingKeyWord = query!!
                viewModel.getRecipeData("Token 9c8b06d329136da358c2d00e76946b0111ce2c48", viewModel.currentPage, viewModel.searchingKeyWord)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchingKeyWord = newText!!
                viewModel.getRecipeData("Token 9c8b06d329136da358c2d00e76946b0111ce2c48", viewModel.currentPage, viewModel.searchingKeyWord)
                return false
            }
        })
    }

    private fun loadNextPage() {
        var handler = Handler()
        var runnable = Runnable {
            viewModel.getRecipeData("Token 9c8b06d329136da358c2d00e76946b0111ce2c48", viewModel.currentPage, viewModel.searchingKeyWord)
            if (viewModel.currentPage == totalPage){
                isLastPage = true
            }
        }
        handler.postDelayed(runnable, 2000)
    }
}