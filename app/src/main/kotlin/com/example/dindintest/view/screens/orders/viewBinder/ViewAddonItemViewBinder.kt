package com.example.dindintest.view.screens.orders.viewBinder

import android.widget.TextView
import com.example.dindintest.R
import com.example.dindintest.data.dao.order.AddOnDAO
import com.example.dindintest.data.dao.order.OrderItemDAO
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View

@Layout(R.layout.view_addon_item)
class ViewAddonItemViewBinder(private val order: OrderItemDAO, private val addon: AddOnDAO) {

    @View(R.id.tvOrderName)
    lateinit var tvOrderName: TextView

    @View(R.id.tvAddOnName)
    lateinit var tvAddOnName: TextView

    @Resolve
    fun onResolve() {
        tvOrderName.text = order.title
        tvAddOnName.text = addon.title
    }
}