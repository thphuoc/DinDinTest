package com.example.dindintest.view.base

import android.os.Bundle
import android.view.View
import com.example.dindintest.R
import kotlinx.android.synthetic.main.view_title_bar.*

abstract class TitleBarFragment : StateLayoutFragment() {
    override val layoutResId: Int = R.layout.fragment_title_bar
    override val autoLoadData: Boolean = true

    abstract fun getTitle(): String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitleBarLabel?.text = getTitle()
    }
}