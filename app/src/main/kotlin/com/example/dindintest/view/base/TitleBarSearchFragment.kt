package com.example.dindintest.view.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.addTextChangedListener
import butterknife.OnClick
import com.example.dindintest.R
import com.example.dindintest.view.exts.goBack
import kotlinx.android.synthetic.main.view_header_search.*

open class TitleBarSearchFragment : StateLayoutFragment() {
    override val layoutResId: Int = R.layout.fragment_search

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { reload() }
    open val triggerSearchOnEmptyText = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtSearch?.addTextChangedListener {
            if (edtSearch.text.toString().isNotBlank() || triggerSearchOnEmptyText) {
                handler.removeCallbacks(searchRunnable)
                handler.postDelayed(searchRunnable, 300L)
            }
        }
    }

    @OnClick(R.id.imgBack)
    open fun onClickBack() {
        goBack()
    }
}