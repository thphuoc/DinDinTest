package com.example.dindintest.view.base

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dindintest.R
import kotlinx.android.synthetic.main.fragment_list.*

open class ListFragment : BaseFragment() {
    override val layoutResId: Int = R.layout.fragment_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placeHolderView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}