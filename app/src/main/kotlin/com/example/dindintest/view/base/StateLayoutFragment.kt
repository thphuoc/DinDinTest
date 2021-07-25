package com.example.dindintest.view.base

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.dindintest.R
import com.example.dindintest.view.base.viewBinder.LoadMoreViewBinder
import com.example.dindintest.view.exts.buildStateEmptyLayout
import com.example.dindintest.view.exts.buildStateInitLayout
import com.example.dindintest.view.exts.buildStateLoadingLayout
import kotlinx.android.synthetic.main.fragment_state_list.*

open class StateLayoutFragment : ListFragment() {
    override val layoutResId: Int = R.layout.fragment_state_list

    private var pageIndex: Int = 0
    open val autoLoadData = false

    /**
     * This variable in order to indicate load data or not when viewpager focused into current fragment
     */
    open val initStateIconResId = R.drawable.ic_empty_search
    open val initStateTextResId = R.string.hint_input_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateLayout?.buildStateEmptyLayout()
        stateLayout?.buildStateInitLayout(customView = {
            it.findViewById<ImageView>(R.id.imgEmpty).setImageResource(initStateIconResId)
            it.findViewById<TextView>(R.id.tvNoResultSearch).setText(initStateTextResId)
        })
        stateLayout?.buildStateLoadingLayout()

        if(autoLoadData) {
            init()
        }

        swipeLayout?.setOnRefreshListener {
            pageIndex = 0
            loadData(pageIndex, ::onLoadCompleted)
        }
    }

    open fun init() {
        stateLayout?.showProgress()
        pageIndex = 0
        loadData(pageIndex, ::onLoadCompleted)
    }

    private fun onLoadCompleted(viewBinders: List<Any>, hasMore: Boolean) {
        placeHolderView?.loadingDone()
        swipeLayout?.isRefreshing = false
        when {
            pageIndex == 0 && viewBinders.isNotEmpty() -> {
                stateLayout?.showContent()
                placeHolderView?.removeAllViews()
                if (hasMore) {
                    placeHolderView?.setLoadMoreResolver(LoadMoreViewBinder {
                        pageIndex++
                        loadData(pageIndex, ::onLoadCompleted)
                    })
                } else {
                    placeHolderView?.noMoreToLoad()
                }
            }
            pageIndex == 0 && viewBinders.isEmpty() -> stateLayout.showEmpty()
            (pageIndex > 0 && viewBinders.isEmpty()) || !hasMore -> {
                placeHolderView?.noMoreToLoad()
            }
        }


        viewBinders.forEach {
            placeHolderView.addView(it)
        }
    }

    fun reload() {
        pageIndex = 0
        stateLayout?.showProgress()
        loadData(pageIndex, ::onLoadCompleted)
    }

    open fun loadData(
        pageIndex: Int,
        onResult: (viewBinders: List<Any>, hasMore: Boolean) -> Unit
    ) {
        onResult(listOf(), false)
    }
}