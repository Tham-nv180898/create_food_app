package com.example.project3.until

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener: RecyclerView.OnScrollListener {

    private var linearLayoutManager:LinearLayoutManager? = null

    constructor(linearLayoutManager: LinearLayoutManager):super(){
        this.linearLayoutManager = linearLayoutManager
    }
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        var visibleItemCount:Int = linearLayoutManager!!.childCount
        var totalItemCount:Int = linearLayoutManager!!.itemCount
        var firstVisibleItemPosition:Int = linearLayoutManager!!.findFirstVisibleItemPosition()

        if (isLoading() || isLastPage()){
            return
        }
        if (firstVisibleItemPosition >= 0 && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount){
            loadMoreItem()
        }
    }

    abstract fun loadMoreItem()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}